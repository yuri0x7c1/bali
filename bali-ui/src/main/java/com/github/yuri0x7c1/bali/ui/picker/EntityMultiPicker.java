package com.github.yuri0x7c1.bali.ui.picker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class EntityMultiPicker<T> extends AbstractField<EntityMultiPicker<T>, List<T>> {
	I18N i18n;

	EntityDataGrid<T> dataGrid;

	VButton selectButton;

	VGrid<T> valueGrid;

	VDialog window;

	DeleteButton deleteButton;

	VButton confirmButton;

	VButton cancelButton;

	public EntityMultiPicker(Class<T> entityType, I18N i18n, EntityDataGrid<T> dataGrid) {
		super(Collections.unmodifiableList(Collections.emptyList()));
		this.i18n = i18n;
		this.dataGrid = dataGrid;

		// addStyleName(BaliStyle.MULTI_PICKER);

		// window
		window = new VDialog();
        // window.setCaption("Bar.select");
        window.setModal(true);
        window.setWidth("80%");
        window.setHeight("80%");

  		// grid
		dataGrid.setSelectionMode(SelectionMode.MULTI);

		// value grid
		valueGrid = new VGrid<>(entityType);

		// select button
		selectButton = new VButton()
			.withIcon(VaadinIcon.PLUS.create())
			/*.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			) */
			.withClickListener(e -> {
				dataGrid.setSelectedItems(new HashSet<T>(((ListDataProvider<T>)valueGrid.getDataProvider()).getItems()));
				window.open();
			});

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
				Set<T> selected = valueGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						Set<T> newValue = new HashSet<>();
						newValue.addAll(getValue());
						newValue.remove(selected.iterator().next());
						setPresentationValue(Collections.unmodifiableList(new ArrayList<T>(newValue)));
					}
				}
			});

		// confirm button
		confirmButton = new VButton("Confirm")
			.withClickListener(e -> {window.close();
				if (!dataGrid.getSelectedItems().isEmpty()) {
					setPresentationValue(Collections.unmodifiableList(new ArrayList<T>(dataGrid.getSelectedItems())));
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
        	dataGrid,
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
	protected void setPresentationValue(List<T> newPresentationValue) {
		setModelValue(newPresentationValue, false);
		valueGrid.setItems(newPresentationValue);
	}

	public void clearColumns() {
		valueGrid.setColumns(new String[]{});
	}

	public void addColumn(String propertyName, String caption) {
		valueGrid.addColumn(propertyName).setHeader(caption);
	}

	public void addColumn(ValueProvider<T, String> valueProvider, String caption) {
		valueGrid.addColumn(valueProvider).setHeader(caption);
	}
}
