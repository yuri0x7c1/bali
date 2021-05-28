package com.github.yuri0x7c1.bali.demo.ui.view;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.datagrid.FooDataGrid;
import com.github.yuri0x7c1.bali.demo.ui.view.main.MainView;
import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.view.EntityListView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
@Route(value = "foo/list", layout = MainView.class)
@PageTitle("Foos")
public class FooListView extends EntityListView<Foo> {
	final FooService fooService;

	public FooListView(EntityDataGrid<Foo> entityDataGrid, I18N i18n, FooDataGrid fooGrid, FooService fooService) {
		super(Foo.class, i18n, entityDataGrid);
		this.fooService = fooService;

		setHeaderText(i18n.get("Foo.Foos"));

		// create handler
		setCreateHandler(() -> {
			getUI().ifPresent(ui -> ui.navigate("foo/create"));
		});

		// edit handler
		setEditHandler(foo -> {
			getUI().ifPresent(ui -> ui.navigate("foo/edit/" + foo.getId().toString()));
		});

		// delete button
		setDeleteHandler(foo -> {
			fooService.delete(foo);
		});
	}
}
