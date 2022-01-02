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

package com.github.yuri0x7c1.bali.ui.card;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;

/**
 *
 * @author yuri0x7c1
 *
 */
public class Card extends MCssLayout {
	MHorizontalLayout headerLayout = new MHorizontalLayout();
	MLabel headerLabel = new MLabel();
	public Card() {
		addStyleName(ValoTheme.LAYOUT_CARD);
        headerLayout.addStyleName("v-panel-caption");
        headerLayout.setWidth("100%");
        headerLayout.addComponent(headerLabel);
        headerLayout.setExpandRatio(headerLabel, 1);
        addComponent(headerLayout);
	}

	public void setHeaderText(String text) {
		headerLabel.setValue(text);
	}

	public void setHeaderMargin(boolean enabled) {
		headerLayout.setMargin(enabled);
	}

	public void addHeaderComponent(Component component) {
		 headerLayout.addComponent(component);
	}

	public void setContent(Component component) {
		addComponent(component);
	}
}