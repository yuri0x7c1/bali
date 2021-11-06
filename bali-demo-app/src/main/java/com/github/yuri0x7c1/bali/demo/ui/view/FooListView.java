package com.github.yuri0x7c1.bali.demo.ui.view;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.sidebar.annotation.SideBarItem;
import org.vaadin.spring.sidebar.annotation.VaadinFontIcon;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.datagrid.FooDataGrid;
import com.github.yuri0x7c1.bali.security.util.SecurityUtil;
import com.github.yuri0x7c1.bali.ui.sidebar.Sections;
import com.github.yuri0x7c1.bali.ui.view.CommonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Secured("ROLE_FOO_VIEW")
@SpringView(name = FooListView.NAME)
@SideBarItem(sectionId = Sections.VIEWS, captionCode = FooListView.CAPTION_CODE)
@VaadinFontIcon(VaadinIcons.LIST)
public class FooListView extends CommonView implements View {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "foo";
	public static final String CAPTION_CODE = "Foo.Foos";

	final I18N i18n;

	final UIEventBus eventBus;

	final ObjectMapper mapper;

	final FooService fooService;

	final FooDataGrid fooDataGrid;

	MButton createButton;

	MButton showButton;

	MButton editButton;

	ConfirmButton deleteButton;

	@PostConstruct
	public void init() {
		// header
		setHeaderText(i18n.get(CAPTION_CODE));

		// create button
		if (SecurityUtil.check("ROLE_FOO_CREATE")) {
			createButton = new MButton(VaadinIcons.PLUS, i18n.get("Create"), event -> create())
				.withDescription(i18n.get("Foo.create"))
				.withStyleName(ValoTheme.BUTTON_PRIMARY);
			addHeaderComponent(createButton);
		}

		// show button
		showButton = new MButton(VaadinIcons.EYE, i18n.get("Show"), event -> {
			Set<Foo> selected = fooDataGrid.getSelectedItems();
			if (CollectionUtils.isNotEmpty(selected)) {
				if (selected.size() == 1) {
					show(selected.iterator().next());
				}
			}
		})
		.withDescription(i18n.get("Foo.show"));
		addHeaderComponent(showButton);

		// edit button
		if (SecurityUtil.check("ROLE_FOO_EDIT")) {
			editButton = new MButton(VaadinIcons.PENCIL, i18n.get("Edit"), event -> {
				Set<Foo> selected = fooDataGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						edit(selected.iterator().next());
					}
				}
			})
			.withDescription(i18n.get("Foo.edit"))
			.withStyleName(ValoTheme.BUTTON_PRIMARY);
			addHeaderComponent(editButton);
		}

		// delete button
		if (SecurityUtil.check("ROLE_FOO_DELETE")) {
			deleteButton = new ConfirmButton(VaadinIcons.TRASH, i18n.get("Delete"), i18n.get("Delete.confirm"), () -> {
				Set<Foo> selected = fooDataGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						delete(selected.iterator().next());
					}
				}
			})
			.withDescription(i18n.get("Foo.delete"))
			.withStyleName(ValoTheme.BUTTON_DANGER);
			addHeaderComponent(deleteButton);
		}

		// expand grid
		expand(fooDataGrid);
	}

	protected void create() {
		getUI().getNavigator().navigateTo(FooCreateView.NAME);
	}

	protected void show(final Foo foo) {
		/*
		try {
			getUI().getNavigator().navigateTo([TODO].NAME + "/" + mapper.writeValueAsString(foo.getId()));
		}
		catch (JsonProcessingException e) {
			log.error("Error convert Foo primary key to json", e);

		}
		*/
	}

	protected void edit(final Foo foo) {
		try {
			getUI().getNavigator().navigateTo(FooEditView.NAME + "/" + mapper.writeValueAsString(foo.getId()));
		}
		catch (JsonProcessingException e) {
			log.error("Error convert Foo primary key to json", e);

		}
	}

	protected void delete(final Foo foo) {
		fooService.delete(foo);
	}
}