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

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import com.github.yuri0x7c1.bali.ui.security.SharedLoginUI;
import com.vaadin.spring.annotation.SpringUI;

@SpringUI(path="/login")
public class LoginUI extends SharedLoginUI {

	@Autowired
	public LoginUI(I18N i18n, VaadinSharedSecurity vaadinSecurity) {
		super(i18n, vaadinSecurity);
	}

}
