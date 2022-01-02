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

package com.github.yuri0x7c1.bali.demo.ui;

import org.vaadin.spring.security.VaadinSecurity;
import org.vaadin.spring.sidebar.components.ValoSideBar;

import com.github.yuri0x7c1.bali.ui.CommonSecuredUI;
import com.github.yuri0x7c1.bali.ui.menu.TopBar;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Theme("mytheme")
@SpringUI
public class MainUI extends CommonSecuredUI {

	public MainUI(SpringViewProvider viewProvider, SpringNavigator navigator, VaadinSecurity vaadinSecurity, TopBar topBar, ValoSideBar sideBar) {
		super(viewProvider, navigator, vaadinSecurity, topBar, sideBar);
	}
}
