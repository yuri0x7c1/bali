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

import java.util.function.Supplier;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.form.AbstractForm;

import com.github.yuri0x7c1.bali.ui.form.EntityForm;
import com.github.yuri0x7c1.bali.ui.form.EntityForm.FormActionType;
import com.github.yuri0x7c1.bali.ui.handler.CancelHandler;
import com.github.yuri0x7c1.bali.ui.handler.CloseHandler;
import com.github.yuri0x7c1.bali.ui.handler.SaveHandler;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.icons.VaadinIcons;

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
public class EntityCreateView<T> extends CommonView {

	@Getter
	private final Class<T> entityType;

	@Getter
	private final I18N i18n;

	@Getter
	private final AbstractForm<T> entityForm;

	@Setter
	private Supplier<T> entitySupplier;

	@Getter
	@Setter
	private SaveHandler<T> saveHandler;

	@Getter
	@Setter
	private CancelHandler<T> cancelHandler;

	@Getter
	@Setter
	private CloseHandler closeHandler;

	@Getter
	private final MButton closeButton;

	public EntityCreateView(Class<T> entityType, I18N i18n, AbstractForm<T> entityForm) {
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityForm = entityForm;

		setHeaderText(this.getClass().getSimpleName());

		// create close button
		closeHandler = () -> UiUtil.back();
		closeButton = UiUtil.createBackButton(i18n.get("Back"), event -> {
			if (closeHandler != null) {
				closeHandler.onClose();
			}
		});
		addHeaderComponent(closeButton);

		// set form action
		if (entityForm instanceof EntityForm) {
			((EntityForm<T>) entityForm).setActionType(FormActionType.CREATE);
		}

		// default entity supplier
		setEntitySupplier(() -> {
			try {
				return entityType.newInstance();
			}
			catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				return null;
			}
		});

		// form save handler
		entityForm.setSavedHandler(e -> {
			if (saveHandler != null) {
				saveHandler.onSave(e);
			}
			if (closeHandler != null) {
				closeHandler.onClose();
			}
		});

		// form reset handler
		entityForm.setResetHandler(e -> {
			if (cancelHandler != null) {
				cancelHandler.onCancel(e);
			}
			if (closeHandler != null) {
				closeHandler.onClose();
			}
		});

		addComponent(entityForm);
	}

	@Override
	public void onEnter() {
		T entity = entitySupplier.get();
		if (entity != null) {
			entityForm.setEntity(entity);
		}
	}

	@Override
	public void onBeforeLeave() {
		entityForm.setEntity(null);
	}
}
