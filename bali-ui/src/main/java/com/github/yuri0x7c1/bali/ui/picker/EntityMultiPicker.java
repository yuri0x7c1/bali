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
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.ValueProvider;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * The Class EntityMultiPicker.
 *
 * @param <T> entity type
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class EntityMultiPicker<T> extends CustomField<List<T>> {
	Class<T> entityType;

	I18N i18n;

	EntityDataGrid<T> dataGrid;

	VGrid<T> valueGrid;

	VDialog dialog;

	VButton selectButton;

	DeleteButton deleteButton;

	VButton confirmButton;

	VButton cancelButton;

	public EntityMultiPicker(Class<T> entityType, I18N i18n, EntityDataGrid<T> dataGrid) {
		super(Collections.unmodifiableList(Collections.emptyList()));
		this.entityType = entityType;
		this.i18n = i18n;
		this.dataGrid = dataGrid;

		// addStyleName(BaliStyle.MULTI_PICKER);

		// window
		dialog = new VDialog();
        // window.setCaption("Bar.select");
        dialog.setModal(true);
        dialog.setWidth("80%");
        dialog.setHeight("80%");

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
				dataGrid.setSelectedItems(Collections.unmodifiableSet(new HashSet<T>(getValue())));
				dialog.open();
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
			.withClickListener(e -> {dialog.close();
				if (!dataGrid.getSelectedItems().isEmpty()) {
					setPresentationValue(Collections.unmodifiableList(new ArrayList<T>(dataGrid.getSelectedItems())));
				}
			});
			//.withStyleName(ValoTheme.BUTTON_PRIMARY);

		// cancel button
		cancelButton = new VButton("Cancel")
			.withClickListener(e -> {
				dialog.close();
			});
			// .withStyleName(ValoTheme.BUTTON_DANGER);

		// set dialog content
		dialog.add(new VVerticalLayout(
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
    protected List<T> generateModelValue() {
        return getValue();
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
