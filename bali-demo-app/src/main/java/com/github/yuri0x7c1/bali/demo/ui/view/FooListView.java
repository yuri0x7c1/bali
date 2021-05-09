package com.github.yuri0x7c1.bali.demo.ui.view;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.datagrid.FooDataGrid;
import com.github.yuri0x7c1.bali.demo.ui.view.main.MainView;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.view.CommonView;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UIScope
@SpringComponent
@Route(value = "foo/list", layout = MainView.class)
@PageTitle("Foos")
public class FooListView extends CommonView {
	final I18N i18n;

	final FooService fooService;

	final FooDataGrid fooGrid;

	VButton createButton;

	VButton showButton;

	VButton editButton;

	DeleteButton deleteButton;

	@PostConstruct
	public void init() {
		setHeaderText(i18n.get("Foo.Foos"));

		// create button
		createButton = new VButton("Create", e -> {
			createButton.getUI().ifPresent(ui -> ui.navigate("foo/create"));
		});


		// edit button
		editButton = new VButton("Edit", e -> {
			Set<Foo> selected = fooGrid.getSelectedItems();
			if (CollectionUtils.isNotEmpty(selected)) {
				if (selected.size() == 1) {
					editButton.getUI().ifPresent(ui ->
						ui.navigate("foo/edit/" + selected.iterator().next().getId().toString())
					);
				}
			}
		});

		// delete button
		deleteButton = new DeleteButton()
			.withText("Delete")
			.withConfirmHandler(() -> {
				Set<Foo> selected = fooGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						fooService.delete(selected.iterator().next());
					}
				}
			});

		addHeaderComponent(createButton);
		addHeaderComponent(editButton);
		addHeaderComponent(deleteButton);

		add(fooGrid);
	}
}