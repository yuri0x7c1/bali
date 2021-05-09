package com.github.yuri0x7c1.bali.demo.ui.picker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.demo.service.BarService;
import com.github.yuri0x7c1.bali.demo.ui.datagrid.BarDataGrid;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@UIScope
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Tag("bar-multi-picker")
public class BarMultiPicker extends AbstractField<BarMultiPicker, List<Bar>> {
	I18N i18n;

	BarService barService;

	BarDataGrid grid;

	VButton selectButton;

	VGrid<Bar> valueGrid;

	VDialog window;

	DeleteButton deleteButton;

	VButton confirmButton;

	VButton cancelButton;

	public BarMultiPicker(I18N i18n, BarService barService, BarDataGrid grid) {
		super(Collections.EMPTY_LIST);
		this.i18n = i18n;
		this.barService = barService;
		this.grid = grid;

		// addStyleName(BaliStyle.MULTI_PICKER);

		// window
		window = new VDialog();
        // window.setCaption("Bar.select");
        window.setModal(true);
        window.setWidth("80%");
        window.setHeight("80%");

  		// grid
		grid.setSelectionMode(SelectionMode.MULTI);

		// select button
		selectButton = new VButton()
			.withIcon(VaadinIcon.PLUS.create())
			/*.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			) */
			.withClickListener(e -> {
				 window.open();
			});

		// value grid
		valueGrid = new VGrid<>(Bar.class);

		// delete button
		deleteButton = new DeleteButton()
			.withIcon(VaadinIcon.TRASH.create())
			// .withDescription(i18n.get("Bar.delete"))
			// .setConfirmationText(i18n.get("Delete.confirm"))
			/*
			.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				ValoTheme.BUTTON_DANGER,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			)
			*/
			.withConfirmHandler(() -> {
				Set<Bar> selected = valueGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						List<Bar> newValue = new ArrayList<>();
						newValue.addAll(getValue());
						newValue.remove(selected.iterator().next());
						setValue(Collections.unmodifiableList(newValue));
					}
				}
			});

		// confirm button
		confirmButton = new VButton("Confirm")
			.withClickListener(e -> {window.close();
				if (!grid.getSelectedItems().isEmpty()) {
					setValue(Collections.unmodifiableList(new ArrayList<>(grid.getSelectedItems())));
				}
			});
			//.withStyleName(ValoTheme.BUTTON_PRIMARY);

		// cancel button
		cancelButton = new VButton("Cancel")
			.withClickListener(e -> {
				window.close();
			});
			// .withStyleName(ValoTheme.BUTTON_DANGER);

		// set window content
		window.add(new VVerticalLayout(
        	grid,
        	new VHorizontalLayout(confirmButton, cancelButton))
        );

		// set content
		getElement().appendChild(
			new VDiv(
				new VDiv(selectButton, deleteButton),
				valueGrid
			).getElement()
		);
	}

	@Override
	protected void setPresentationValue(List<Bar> newPresentationValue) {
		setValue(newPresentationValue);
		valueGrid.setItems(newPresentationValue);
	}

	public BarMultiPicker withCaption(String caption) {
		// setCaption(caption);
		return this;
	}

	public BarMultiPicker clearColumns() {
		valueGrid.setColumns(new String[]{});
		return this;
	}

	public BarMultiPicker withColumn(String propertyName, String caption) {
		valueGrid.addColumn(propertyName).setHeader(caption);
		return this;
	}

	public BarMultiPicker withColumn(ValueProvider<Bar, String> valueProvider, String caption) {
		valueGrid.addColumn(valueProvider).setHeader(caption);
		return this;
	}

	public BarMultiPicker clearDataGridColumns() {
		grid.clearColumns();
		return this;
	}

	public BarMultiPicker withDataGridColumn(String propertyName, String caption) {
		grid.addColumn(propertyName, caption);
		return this;
	}

	public BarMultiPicker withDataGridColumn(ValueProvider<Bar, String> valueProvider, String caption) {
		grid.addColumn(valueProvider, caption);
		return this;
	}

	public BarMultiPicker withHeaderVisble(boolean headerVisible) {
		// valueGrid.setHeaderVisible(headerVisible);
		return this;
	}



}
