package com.github.yuri0x7c1.bali.demo.ui.view;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.datagrid.FooDataGrid;
import com.github.yuri0x7c1.bali.demo.ui.layout.ApplicationLayout;

import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.view.EntityListView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = FooListView.ROUTE, layout = ApplicationLayout.class)
public class FooListView extends EntityListView<Foo> {
	public static final String ROUTE = "foo/list";
	public static final String TITLE_CODE = "Foo.Foos";

	final FooService fooService;

	public FooListView(I18N i18n, FooDataGrid fooDataGrid, FooService fooService) {
		super(Foo.class, i18n, fooDataGrid);
		this.fooService = fooService;

		setHeaderText(i18n.get(TITLE_CODE));

		setCreateHandler(() -> {
			getUI().ifPresent(ui -> ui.navigate(FooCreateView.class));
		});

		setEditHandler(foo -> {
			getUI().ifPresent(ui -> ui.navigate(FooEditView.class, foo.getId()));
		});

		setDeleteHandler(foo -> {
			fooService.delete(foo);
		});
	}
}
