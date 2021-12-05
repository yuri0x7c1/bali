package com.github.yuri0x7c1.bali.demo.ui.view;

import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;

import com.github.yuri0x7c1.bali.data.search.util.SearchUtil;
import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.datagrid.FooDataGrid;
import com.github.yuri0x7c1.bali.demo.ui.sidebar.Sections;
import com.github.yuri0x7c1.bali.ui.view.EntityListView;
import com.github.yuri0x7c1.bali.ui.view.ParametrizedView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringView;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE)
@Secured("ROLE_FOO_VIEW")
@SpringView(name = FooListView.NAME)
@SideBarItem(sectionId = Sections.VIEWS, captionCode = FooListView.CAPTION_CODE)
@VaadinFontIcon(VaadinIcons.LIST)
public class FooListView extends EntityListView<Foo> {
	public static final String NAME = "foos";
	public static final String CAPTION_CODE = "Foo.Foos";

	final FooService fooService;

	public FooListView(I18N i18n, FooDataGrid fooDataGrid, FooService fooService) {
		super(Foo.class, i18n, fooDataGrid);
		this.fooService = fooService;

		setHeaderText(i18n.get(CAPTION_CODE));

		setCreateHandler(() -> getUI().getNavigator().navigateTo(FooEditView.NAME));

		setExportPageProvider(pageable -> fooService
				.findAll(SearchUtil.buildSpecification(Foo.class, fooDataGrid.getSearchModel()), pageable));

		fooDataGrid.setShowHandler(e -> ParametrizedView.navigateTo(FooShowView.NAME, e.getId()));

		fooDataGrid.setEditHandler(e -> ParametrizedView.navigateTo(FooEditView.NAME, e.getId()));

		fooDataGrid.setDeleteHandler(e -> fooService.delete(e));

	}
}
