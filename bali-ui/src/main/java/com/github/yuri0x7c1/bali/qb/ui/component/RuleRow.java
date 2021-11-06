package com.github.yuri0x7c1.bali.qb.ui.component;

import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.github.yuri0x7c1.bali.data.qb.model.QbField;
import com.github.yuri0x7c1.bali.data.qb.model.QbOperator;
import com.github.yuri0x7c1.bali.data.qb.model.QbType;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
public class RuleRow extends MHorizontalLayout {
	QueryBuilder qb;

	GroupRow parentGroup;

	ComboBox<QbField> fieldSelect;

	ComboBox<QbOperator> operatorSelect;

	MCssLayout valueLayout;

	AbstractField valueField;

	MButton deleteButton;

	/**
	 * Instantiates a new rule row.
	 *
	 * @param fields the fields
	 */
	public RuleRow(QueryBuilder qb, GroupRow parentGroup) {
		this.qb = qb;
		this.parentGroup = parentGroup;

		operatorSelect = new ComboBox<>();
		operatorSelect.addStyleName(ValoTheme.COMBOBOX_SMALL);

		fieldSelect = new ComboBox<>();
		fieldSelect.addStyleName(ValoTheme.COMBOBOX_SMALL);
		fieldSelect.setItems(qb.getConfig().getFields());

		fieldSelect.addValueChangeListener(event -> {
			if (event.getValue() != null) {
				setOperators(event.getValue());
				createValueField(event.getValue());
			}
		});

		valueLayout = new MCssLayout();

		deleteButton = new MButton(VaadinIcons.CLOSE, "Delete", event -> parentGroup.deleteRule(this))
			.withStyleName(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);


		addComponent(fieldSelect);
		addComponent(operatorSelect);
		addComponent(valueLayout);
		addComponent(deleteButton);
	}

	/**
	 * Sets the operators.
	 *
	 * @param field the new operators
	 */
	private void setOperators(QbField field) {
		operatorSelect.setItems(field.getType().getOperators());
	}

	/**
	 * Creates the value field.
	 *
	 * @param field the field
	 */
	private void createValueField(QbField field) {
		if (QbType.STRING.equals(field.getType())) {
			if (valueField != null) {
				valueLayout.removeComponent(valueField);
			}
			valueField = new MTextField().withStyleName(ValoTheme.TEXTFIELD_SMALL);
			valueLayout.addComponent(valueField);
		}
	}

	/**
	 * Gets the selected field.
	 *
	 * @return the selected field
	 */
	public QbField getSelectedField() {
		return fieldSelect.getValue();
	}

	/**
	 * Gets the selected field name.
	 *
	 * @return the selected field name
	 */
	public String getSelectedFieldName() {
		if (fieldSelect.getValue() != null) {
			return fieldSelect.getValue().getName();
		}

		return null;
	}

	/**
	 * Gets the selected operator.
	 *
	 * @return the selected operator
	 */
	public QbOperator getSelectedOperator() {
		return operatorSelect.getValue();
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return valueField.getValue();
	}
}
