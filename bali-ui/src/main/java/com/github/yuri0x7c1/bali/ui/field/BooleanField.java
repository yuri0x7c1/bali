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

package com.github.yuri0x7c1.bali.ui.field;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.github.yuri0x7c1.bali.context.ApplicationContextProvider;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;

/**
 *
 * @author yuri0x7c1
 *
 */
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BooleanField extends ComboBox<Boolean> {
	private final I18N i18n;

	public BooleanField() {
		this(ApplicationContextProvider.getContext().getBean(I18N.class));
	}

	public BooleanField(String caption) {
		this(ApplicationContextProvider.getContext().getBean(I18N.class));
		setCaption(caption);
	}

	public BooleanField(I18N i18n) {
		this.i18n = i18n;

		setItems(new Boolean[] {Boolean.TRUE, Boolean.FALSE});
		setItemCaptionGenerator(item -> {
			if (item != null) {
				return i18n.get(item);
			}
			return "";
		});
	}
}
