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

package com.github.yuri0x7c1.bali.ui;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.security.VaadinSecurity;

import com.github.appreciated.app.layout.builder.CDIAppLayoutBuilder;
import com.github.appreciated.app.layout.component.button.IconButton;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.navigator.SpringViewProvider;

import lombok.Getter;

public abstract class CommonSecuredUI extends CommonUI {

	@Getter
	private final VaadinSecurity vaadinSecurity;

	public CommonSecuredUI(I18N i18n, SpringViewProvider springViewProvider, VaadinSecurity vaadinSecurity) {
		super(i18n, springViewProvider);
		this.vaadinSecurity = vaadinSecurity;
	}

	@Override
	protected CDIAppLayoutBuilder initAppLayoutBuilder() {
		return super.initAppLayoutBuilder()
				.addToAppBar(new IconButton(VaadinIcons.POWER_OFF, event -> vaadinSecurity.logout()));
	}
}
