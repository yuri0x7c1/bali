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

package com.github.yuri0x7c1.bali.ui.header;

import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class Header extends MHorizontalLayout {
	MLabel label;
	MHorizontalLayout componentContainer;

	public Header() {
		setWidthFull();
		setMargin(true);
		setSpacing(true);
		label = new MLabel();
		componentContainer = new MHorizontalLayout();

	    add(label);
	    add(componentContainer);

		setComponentAlignment(label, Alignment.MIDDLE_LEFT);
		setComponentAlignment(componentContainer, Alignment.MIDDLE_RIGHT);
	}

	public void setText(String text) {
		label.setValue(text);
	}

	public void setTextSize(HeaderTextSize size) {
		label.addStyleName(size.name().toLowerCase());
	}

	public void addHeaderComponent(Component c) {
		componentContainer.add(c);
	}
}
