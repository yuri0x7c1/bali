package com.github.yuri0x7c1.bali.ui.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.collections4.CollectionUtils;
import org.vaadin.firitin.components.button.DeleteButton;
import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.dialog.VDialog;
import org.vaadin.firitin.components.grid.VGrid;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.orderedlayout.VVerticalLayout;
import org.vaadin.firitin.form.AbstractForm;

import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.ValueProvider;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/**
 * The Class EntityMultiPicker.
 *
 * @param <T> entity type
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class EntityMultiEditor<T> extends AbstractField<EntityMultiEditor<T>, List<T>> {

	Class<T> entityType;

	I18N i18n;

	AbstractForm<T> form;

	VGrid<T> valueGrid;

	VDialog dialog;

	VButton createButton;

	VButton editButton;

	DeleteButton deleteButton;

	VButton confirmButton;

	VButton cancelButton;

	@Getter
	@Setter
	@NonFinal
	Supplier<T> newEntitySupplier;

	public EntityMultiEditor(Class<T> entityType, I18N i18n, AbstractForm<T> form) {
		super(Collections.unmodifiableList(Collections.emptyList()));
		this.entityType = entityType;
		this.i18n = i18n;
		this.form = form;

		// dialog
		dialog = new VDialog();
        // window.setCaption("Bar.select");
        dialog.setModal(true);
        dialog.setWidth("80%");
        dialog.setHeight("80%");

		// value grid
		valueGrid = new VGrid<>(entityType);

		// create button
		createButton = new VButton()
			.withIcon(VaadinIcon.PLUS.create())
			.withClickListener(e -> {
				if (newEntitySupplier != null) {
					form.setEntity(newEntitySupplier.get());
				}
				else {
					try {
						form.setEntity(entityType.getDeclaredConstructor().newInstance());
					}
					catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
				dialog.open();
			});

		// edit button
		editButton = new VButton()
			.withIcon(VaadinIcon.PENCIL.create())
			.withClickListener(e -> {
				Set<T> selected = valueGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						form.setEntity(selected.iterator().next());
						dialog.open();
					}
				}
			});

		// delete button
		deleteButton = new DeleteButton()
			.withIcon(VaadinIcon.TRASH.create())
			.withConfirmHandler(() -> {
				Set<T> selected = valueGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						List<T> newValue = new ArrayList<>();
						newValue.addAll(getValue());
						newValue.remove(selected.iterator().next());
						setPresentationValue(Collections.unmodifiableList(newValue));
					}
				}
			});

		// confirm button
		confirmButton = new VButton("Confirm")
			.withClickListener(e -> {
				dialog.close();
				List<T> newValue = new ArrayList<>(getValue());
				newValue.add(form.getEntity());
				setPresentationValue(Collections.unmodifiableList(newValue));
				form.setEntity(null);
			});
			//.withStyleName(ValoTheme.BUTTON_PRIMARY);

		// cancel button
		cancelButton = new VButton("Cancel")
			.withClickListener(e -> {
				dialog.close();
				form.setEntity(null);
			});
			// .withStyleName(ValoTheme.BUTTON_DANGER);

		// set dialog content
		dialog.add(new VVerticalLayout(
        	form,
        	new VHorizontalLayout(confirmButton, cancelButton))
        );

		// set content
		getElement().appendChild(
			new VDiv(
				new VDiv(createButton, editButton, deleteButton),
				valueGrid
			).getElement()
		);
	}

	@Override
	protected void setPresentationValue(List<T> newPresentationValue) {
		setModelValue(newPresentationValue, false);
		valueGrid.setItems(newPresentationValue);
	}

	public void setCreateEntitySupplier(Supplier<T> createSupplier) {

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
