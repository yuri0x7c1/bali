package com.github.yuri0x7c1.bali.ui.field;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.ComboBox;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level=AccessLevel.PRIVATE)
public class BooleanField extends ComboBox<Boolean> {
	String caption;
	
	String trueCaption;
	
	String falseCaption;
	
	public BooleanField(String caption, String trueCaption, String falseCaption) {
		super();
		this.caption = caption;
		this.trueCaption = trueCaption;
		this.falseCaption = falseCaption;
		
		setCaption(caption);
		setItems(new Boolean[] {Boolean.TRUE, Boolean.FALSE});
		setItemCaptionGenerator(item -> {
			if (Boolean.TRUE.equals(item)) {
				if (StringUtils.isNotBlank(trueCaption)) {
					return trueCaption;
				}
				return Boolean.TRUE.toString();
			}
			else if (Boolean.FALSE.equals(item)) {
				if (StringUtils.isNotBlank(falseCaption)) {
					return falseCaption;
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
