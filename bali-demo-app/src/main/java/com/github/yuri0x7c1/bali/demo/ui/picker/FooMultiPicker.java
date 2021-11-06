package com.github.yuri0x7c1.bali.demo.ui.picker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.datagrid.FooDataGrid;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FooMultiPicker extends CustomField<List<Foo>>{
	final I18N i18n;

	final UIEventBus eventBus;

	final FooService fooService;

	final FooDataGrid grid;

	MButton selectButton;

	private MGrid<Foo> valueGrid;

	Window window;

	ConfirmButton deleteButton;

	MButton confirmButton;

	MButton cancelButton;

	List<Foo> value;

	@PostConstruct
	public void init() {
		addStyleName(BaliStyle.MULTI_PICKER);

		// window
		window = new Window();
        window.setCaption("Foo.select");
        window.setModal(true);
        window.setWidth("80%");
        window.setHeight("80%");

  		// grid
		grid.setSelectionMode(SelectionMode.MULTI);

		// select button
		selectButton = new MButton(VaadinIcons.PLUS)
			.withDescription(i18n.get("Foo.select"))
			.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			)
			.withListener(e -> {
				 getUI().addWindow(window);
			});

		// value grid
		valueGrid = new MGrid<>(Foo.class);

		// delete button
		deleteButton = new ConfirmButton()
			.withIcon(VaadinIcons.TRASH)
			.withDescription(i18n.get("Foo.delete"))
			.setConfirmationText(i18n.get("Delete.confirm"))
			.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				ValoTheme.BUTTON_DANGER,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			)
			.addClickListener(() -> {
				Set<Foo> selected = valueGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						List<Foo> newValue = new ArrayList<>();
						newValue.addAll(getValue());
						newValue.remove(selected.iterator().next());
						setValue(Collections.unmodifiableList(newValue));
					}
				}
			});

		// confirm button
		confirmButton = new MButton("Confirm")
			.withListener(e -> {window.close();
				if (!grid.getSelectedItems().isEmpty()) {
					setValue(Collections.unmodifiableList(new ArrayList<>(grid.getSelectedItems())));
				}
			})
			.withStyleName(ValoTheme.BUTTON_PRIMARY);

		// cancel button
		cancelButton = new MButton("Cancel")
			.withListener(e -> {
				window.close();
			})
			.withStyleName(ValoTheme.BUTTON_DANGER);

		// set window content
		window.setContent(new MVerticalLayout(
        	grid,
        	new MHorizontalLayout(confirmButton, cancelButton))
        );
	}

	@Override
	protected Component initContent() {
		return new MCssLayout(
			new MCssLayout(selectButton, deleteButton),
			valueGrid
		);
	}

	@Override
	protected void doSetValue(List<Foo> value) {
		this.value = value;
		valueGrid.setItems(value);
	}

	@Override
	public List<Foo> getValue() {
		return value;
	}

	public FooMultiPicker withCaption(String caption) {
		setCaption(caption);
		return this;
	}

	public FooMultiPicker clearColumns() {
		valueGrid.setColumns(new String[]{});
		return this;
	}

	public FooMultiPicker withColumn(String propertyName, String caption) {
		valueGrid.addColumn(propertyName).setCaption(caption);
		return this;
	}

	public FooMultiPicker withColumn(ValueProvider<Foo, String> valueProvider, String caption) {
		valueGrid.addColumn(valueProvider).setCaption(caption);
		return this;
	}

	public FooMultiPicker clearDataGridColumns() {
		grid.clearColumns();
		return this;
	}

	public FooMultiPicker withDataGridColumn(String propertyName, String caption) {
		grid.withColumn(propertyName, caption);
		return this;
	}

	public FooMultiPicker withDataGridColumn(ValueProvider<Foo, String> valueProvider, String caption) {
		grid.withColumn(valueProvider, caption);
		return this;
	}

	public FooMultiPicker withHeaderVisble(boolean headerVisible) {
		valueGrid.setHeaderVisible(headerVisible);
		return this;
	}
}
