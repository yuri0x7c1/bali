package com.github.yuri0x7c1.bali.ui.field;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.firitin.components.combobox.VComboBox;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * Boolean field
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
public class BooleanField extends VComboBox<Boolean> {
	String label;

	String trueLabel;

	String falseLabel;

	public BooleanField(String label, String trueLabel, String falseLabel) {
		super();
		this.label = label;
		this.trueLabel = trueLabel;
		this.falseLabel = falseLabel;

		setLabel(label);
		setItems(new Boolean[] {Boolean.TRUE, Boolean.FALSE});
		setItemLabelGenerator(item -> {
			if (Boolean.TRUE.equals(item)) {
				if (StringUtils.isNotBlank(trueLabel)) {
					return trueLabel;
				}
				return Boolean.TRUE.toString();
			}
			else if (Boolean.FALSE.equals(item)) {
				if (StringUtils.isNotBlank(falseLabel)) {
					return falseLabel;
				}
				return Boolean.FALSE.toString();
			}
			return "";
		});
	}

	public BooleanField (String caption) {
		this(caption, null, null);
	}
}
