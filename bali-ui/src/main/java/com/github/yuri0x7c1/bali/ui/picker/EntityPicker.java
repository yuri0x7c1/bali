package com.github.yuri0x7c1.bali.ui.picker;

import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.html.VLabel;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;

import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.icon.VaadinIcon;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * Entity picker
 *
 * @param <T> entity type
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class EntityPicker<T> extends AbstractField<EntityPicker<T>, T> {
	Class<T> entityType;

	I18N i18n;

	EntityDataGrid<T> dataGrid;

	VLabel valueLabel;

	VDialog dialog;

	VButton selectButton;

	DeleteButton deleteButton;

	VButton confirmButton;

	VButton cancelButton;

	public EntityPicker(Class<T> entityType, I18N i18n, EntityDataGrid<T> dataGrid) {
		super(null);
		this.entityType = entityType;
		this.i18n = i18n;
		this.dataGrid = dataGrid;

		// addStyleName(BaliStyle.MULTI_PICKER);

		// window
		dialog = new VDialog();
        // .setCaption("Bar.select");
        dialog.setModal(true);
        dialog.setWidth("80%");
        dialog.setHeight("80%");

  		// grid
		dataGrid.setSelectionMode(SelectionMode.SINGLE);

		// value grid
		valueLabel = new VLabel();

		// select button
		selectButton = new VButton()
			.withIcon(VaadinIcon.PLUS.create())
			/*.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			) */
			.withClickListener(e -> {
				dataGrid.setSelectedItem(getValue());
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
				setPresentationValue(null);
			});

		// confirm button
		confirmButton = new VButton("Confirm")
			.withClickListener(e -> {dialog.close();
				if (!dataGrid.getSelectedItems().isEmpty()) {
					setPresentationValue(dataGrid.getFirstSelectedItem().orElse(null));
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
				valueLabel,selectButton, deleteButton
			).getElement()
		);
	}

	@Override
	protected void setPresentationValue(T newPresentationValue) {
		setModelValue(newPresentationValue, false);
		valueLabel.setText(String.valueOf(newPresentationValue));
	}
}
