package com.github.yuri0x7c1.bali.ui.form;

import java.lang.reflect.Field;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.vaadin.viritin.form.AbstractForm;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntityForm<T> extends AbstractForm<T> {

	public static final int DEFAULT_RESPONSIVE_FORM_XS = 12;
	public static final int DEFAULT_RESPONSIVE_FORM_SM = 6;
	public static final int DEFAULT_RESPONSIVE_FORM_MD = 6;
	public static final int DEFAULT_RESPONSIVE_FORM_LG = 4;

	public EntityForm(Class<T> entityType) {
		super(entityType);
		addStyleName(BaliStyle.FORM);
		setSizeUndefined();
	}

	@Override
	protected Component createContent() {
		ResponsiveLayout layout = new ResponsiveLayout();
		EntityForm.setResponsiveLayoutDefaultRules(layout);
		log.info(this.getClass().getName());

		for (Field field : this.getClass().getDeclaredFields()) {
			log.info("!!! 1). field : {}", field.getName());
			if (ClassUtils.getAllInterfaces(field.getType()).contains(HasValue.class)
					&& ClassUtils.getAllInterfaces(field.getType()).contains(Component.class)) {
				log.info("!!! 2). field : {}", field.getName());
				try {
					layout.addRow().addComponent((Component) FieldUtils.readDeclaredField(this, field.getName(), true));
				} catch (IllegalAccessException ex) {
					log.error(ex.getMessage(), ex);
				}
			}
		}

		HorizontalLayout toolbar = getToolbar();
		toolbar.setStyleName(BaliStyle.FORM_TOOLBAR);
		layout.addRow().addColumn().withComponent(toolbar);

		return layout;
	}

	public static void setResponsiveLayoutDefaultRules(ResponsiveLayout layout) {
		layout.setDefaultRules(DEFAULT_RESPONSIVE_FORM_XS, DEFAULT_RESPONSIVE_FORM_SM, DEFAULT_RESPONSIVE_FORM_MD,
				DEFAULT_RESPONSIVE_FORM_LG);
	}

}
