package com.github.yuri0x7c1.bali.ui.field;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.i18n.I18N;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BooleanField extends ComboBox<Boolean> {
	I18N i18n;

	public BooleanField(I18N i18n) {
		this.i18n = i18n;

		setItems(new Boolean[] {Boolean.TRUE, Boolean.FALSE});
		setItemCaptionGenerator(item -> {
			if (item != null) {
				return i18n.get(String.valueOf(item));
			}
			return "";
		});
	}
}
