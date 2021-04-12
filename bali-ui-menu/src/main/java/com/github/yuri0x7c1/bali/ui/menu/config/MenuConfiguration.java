/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.yuri0x7c1.bali.ui.menu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.menu.MenuUtils;
import com.github.yuri0x7c1.bali.ui.menu.VaadinFontIconProvider;

/**
 * Spring configuration for the {@link org.vaadin.spring.sidebar.components.AccordionSideBar} and its dependencies.
 *
 * @author Petter Holmström (petter@vaadin.com)
 * @author yuri0x7c1
 * @see com.github.yuri0x7c1.bali.ui.menu.annotation.EnableMenu
 */
@Configuration
public class MenuConfiguration {

    @Autowired
    I18N i18n;

    @Autowired
    ApplicationContext applicationContext;

    /*
    @Bean
    @UIScope
    AccordionSideBar accordionSideBar() {
        return new AccordionSideBar(sideBarUtils());
    }

    @Bean
    @UIScope
    ValoSideBar valoSideBar() {
        return new ValoSideBar(sideBarUtils());
    }
    */

    @Bean
    MenuUtils menuUtils() {
        return new MenuUtils(applicationContext, i18n);
    }

    @Bean
    VaadinFontIconProvider vaadinFontIconProvider() {
        return new VaadinFontIconProvider();
    }
}
