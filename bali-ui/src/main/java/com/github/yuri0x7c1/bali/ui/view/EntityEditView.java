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

package com.github.yuri0x7c1.bali.ui.view;

import java.util.function.Function;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.form.AbstractForm;

import com.github.yuri0x7c1.bali.ui.form.EntityForm;
import com.github.yuri0x7c1.bali.ui.form.EntityForm.FormActionType;
import com.github.yuri0x7c1.bali.ui.handler.CancelHandler;
import com.github.yuri0x7c1.bali.ui.handler.SaveHandler;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 * @param <P>
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityEditView<T, P> extends ParametrizedView<P> {

	private final Class<T> entityType;

	private final I18N i18n;

	@Getter
	private final AbstractForm<T> entityForm;

	@Setter
	private Function<P, T> entityProvider;

	@Getter
	@Setter
	private SaveHandler<T> saveHandler;

	@Getter
	@Setter
	private CancelHandler<T> cancelHandler;

	public EntityEditView(Class<T> entityType, Class<P> paramsType, I18N i18n, AbstractForm<T> entityForm) {
		super(paramsType);
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityForm = entityForm;

		setHeaderText(this.getClass().getSimpleName());

		addHeaderComponent(UiUtil.createBackButton(i18n.get("Back"), e -> UiUtil.back()));

		// set form action
		if (entityForm instanceof EntityForm) {
			((EntityForm<T>) entityForm).setActionType(FormActionType.EDIT);
		}

		// form save handler
		entityForm.setSavedHandler(e -> {
			if (saveHandler != null) {
				saveHandler.onSave(e);
			}
			UiUtil.back();
		});

		// form reset handler
		entityForm.setResetHandler(e -> {
			if (cancelHandler != null) {
				cancelHandler.onCancel(e);
			}
			UiUtil.back();
		});

		addComponent(entityForm);
	}

	@Override
	public void onEnter() {
		if (getParams() != null) {
			T entity = entityProvider.apply(getParams());
			if (entity != null) {
				entityForm.setEntity(entity);
			}
		}
	}

	@Override
	public void onBeforeLeave() {
		entityForm.setEntity(null);
	}
}
