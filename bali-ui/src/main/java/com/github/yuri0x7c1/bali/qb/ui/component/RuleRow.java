package com.github.yuri0x7c1.bali.qb.ui.component;

import org.vaadin.firitin.components.button.VButton;
import org.vaadin.firitin.components.html.VDiv;
import org.vaadin.firitin.components.orderedlayout.VHorizontalLayout;
import org.vaadin.firitin.components.textfield.VTextField;

import com.github.yuri0x7c1.bali.data.qb.model.QbField;
import com.github.yuri0x7c1.bali.data.qb.model.QbOperator;
import com.github.yuri0x7c1.bali.data.qb.model.QbType;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * The Class RuleRow.
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class RuleRow extends VHorizontalLayout {

	/** The qb. */
	QueryBuilder qb;

	/** The parent group. */
	GroupRow parentGroup;

	/** The field select. */
	ComboBox<QbField> fieldSelect;

	/** The operator select. */
	ComboBox<QbOperator> operatorSelect;

	/** The value layout. */
	VDiv valueLayout;

	/** The value field. */
	AbstractField valueField;

	/** The delete button. */
	VButton deleteButton;

	/**
	 * Instantiates a new rule row.
	 *
	 * @param qb the qb
	 * @param parentGroup the parent group
	 */
	public RuleRow(QueryBuilder qb, GroupRow parentGroup) {
		this.qb = qb;
		this.parentGroup = parentGroup;

		operatorSelect = new ComboBox<>();

		fieldSelect = new ComboBox<>();
		// fieldSelect.addStyleName(ValoTheme.COMBOBOX_SMALL);
		fieldSelect.setItems(qb.getConfig().getFields());

		fieldSelect.addValueChangeListener(event -> {
			if (event.getValue() != null) {
				setOperators(event.getValue());
				createValueField(event.getValue());
			}
		});

		valueLayout = new VDiv();

		deleteButton = new VButton("Delete", VaadinIcon.CLOSE.create(),  event -> parentGroup.deleteRule(this))
			.withThemeVariants(ButtonVariant.LUMO_ERROR);


		add(fieldSelect);
		add(operatorSelect);
		add(valueLayout);
		add(deleteButton);
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
				valueLayout.remove(valueField);
			}
			valueField = new VTextField();
			valueLayout.add(valueField);
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
