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

package com.github.yuri0x7c1.bali.ui.menu;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.sidebar.components.ValoSideBar;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;

import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.themes.ValoTheme;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@UIScope
@SpringComponent
public class TopBar extends CssLayout {
	@Autowired
	ValoSideBar sideBar;

	MButton collapseButton = new MButton(VaadinIcons.MENU)
			.withStyleName(ValoTheme.BUTTON_LINK, "collapse-button");

	MButton logoutButton;

	MLabel userLabel;

	public TopBar() {
		addStyleNames(BaliStyle.TOP_BAR);
		addComponent(collapseButton);
	}

	public TopBar withLogoutButton(ClickListener listener) {
		logoutButton = new MButton(VaadinIcons.POWER_OFF)
				.withStyleName(ValoTheme.BUTTON_LINK, "logout-button");
		logoutButton.withListener(listener);
		addComponent(logoutButton);
		return this;
	}

	@PostConstruct
	public void init() {
		/*
		userLabel = new MLabel(vaadinSecurity.getAuthentication().getName())
			.withStyleName(ValoTheme.TEXTFIELD_ALIGN_RIGHT, "user-label");
		addComponent(userLabel);
		*/

		collapseButton.withListener(e -> {
			sideBar.setLargeIcons(!sideBar.isLargeIcons());
		});
	}
}