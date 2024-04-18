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
import org.vaadin.viritin.button.MButton;

import com.github.yuri0x7c1.bali.ui.detail.EntityDetail;
import com.github.yuri0x7c1.bali.ui.handler.CloseHandler;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.icons.VaadinIcons;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 * @param <P>
 */
public class EntityShowView<T, P> extends ParametrizedView<P> {

	@Getter
	private final Class<T> entityType;

	@Getter
	private final I18N i18n;

	@Getter
	private final EntityDetail<T> entityDetail;

	@Getter
	private final Function<P, T> entityProvider;

	@Getter
	@Setter
	private CloseHandler closeHandler;

	@Getter
	private final MButton closeButton;

	public EntityShowView(Class<T> entityType, Class<P> paramsType, I18N i18n, EntityDetail<T> entityDetail,
			Function<P, T> entityProvider) {
		super(paramsType);
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityDetail = entityDetail;
		this.entityProvider = entityProvider;

		setHeaderText(this.getClass().getSimpleName());

		// create close button
		closeHandler = () -> UiUtil.back();
		closeButton = UiUtil.createBackButton(i18n.get("Back"), event -> {
			if (closeHandler != null) closeHandler.onClose();
		});
		addHeaderComponent(closeButton);

		addComponent(entityDetail);
	}

	@Override
	public void onEnter() {
		if (getParams() != null) {
			T entity = entityProvider.apply(getParams());
			if (entity != null) {
				entityDetail.setEntity(entity);
			}
		}
	}
}
