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

import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.ui.header.Header;
import com.github.yuri0x7c1.bali.ui.header.HeaderTextSize;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class CommonView extends MVerticalLayout implements View {

 	final Header header;

	public CommonView() {
		setMargin(false);
		addStyleName(BaliStyle.COMMON_VIEW);

		header = new Header();
		header.setTextSize(HeaderTextSize.H2);
		header.setMargin(false);

		addComponent(header);
	}

	public void setHeaderText(String text) {
		header.setText(text);
	}

	public void addHeaderComponent(Component c) {
		header.addHeaderComponent(c);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		onEnter();
	}

	public void onEnter() {};
}
