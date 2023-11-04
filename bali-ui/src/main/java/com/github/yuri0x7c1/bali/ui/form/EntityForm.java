/*
 * Copyright 2021-2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.yuri0x7c1.bali.ui.form;

import java.lang.reflect.Field;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.vaadin.viritin.form.AbstractForm;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 */
@Slf4j
public class EntityForm<T> extends AbstractForm<T> {

	public static final int DEFAULT_RESPONSIVE_FORM_XS = 12;
	public static final int DEFAULT_RESPONSIVE_FORM_SM = 6;
	public static final int DEFAULT_RESPONSIVE_FORM_MD = 6;
	public static final int DEFAULT_RESPONSIVE_FORM_LG = 4;

	public enum FormActionType {
		CREATE,
		EDIT;
	}

	@Getter
	@Setter
	private FormActionType actionType;

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
			if (ClassUtils.getAllInterfaces(field.getType()).contains(HasValue.class)
					&& ClassUtils.getAllInterfaces(field.getType()).contains(Component.class)) {
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

	@Override
	protected Button createSaveButton() {
		Button b = super.createSaveButton();
		b.setIcon(VaadinIcons.CHECK);
		return b;
	}

	@Override
	protected Button createResetButton() {
		Button b = super.createResetButton();
		b.setIcon(VaadinIcons.CLOSE);
		return b;
	}

	public static void setResponsiveLayoutDefaultRules(ResponsiveLayout layout) {
		layout.setDefaultRules(DEFAULT_RESPONSIVE_FORM_XS, DEFAULT_RESPONSIVE_FORM_SM, DEFAULT_RESPONSIVE_FORM_MD,
				DEFAULT_RESPONSIVE_FORM_LG);
	}

}
