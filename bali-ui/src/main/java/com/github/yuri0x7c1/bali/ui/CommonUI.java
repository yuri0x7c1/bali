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

import java.util.Locale;

import org.vaadin.spring.i18n.I18N;

import com.github.appreciated.app.layout.AppLayout;
import com.github.appreciated.app.layout.behaviour.AppLayoutComponent;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.CDIAppLayoutBuilder;
import com.github.appreciated.app.layout.builder.factories.DefaultSpringNavigationElementInfoProducer;
import com.github.yuri0x7c1.bali.ui.view.ErrorView;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@PushStateNavigation
@Viewport("width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no")
@StyleSheet("vaadin://bali.css")
@Theme("bali_default")
@Widgetset("BaliDefaultWidgetset")
public abstract class CommonUI extends UI {

	@Getter
	private final I18N i18n;

    private final SpringViewProvider springViewProvider;

    @Getter
    private AppLayoutComponent appLayout;

    protected void setDefaultLocale(Locale locale) {
        // Call to affect this current UI. Workaround for bug: http://dev.vaadin.com/ticket/12350
    	this.setLocale(locale);

    	// Affects only future UI instances, not current one because of bug. See workaround in line above.
    	this.getSession().setLocale(locale);
    }

	@Override
	protected void init(VaadinRequest request) {
    	// set default locale
    	setDefaultLocale(new Locale("en", "en_US"));

    	// set application name
        getPage().setTitle(i18n.get("Application.name"));

        // build application layout
        CDIAppLayoutBuilder appLayoutBuilder = createAppLayoutBuilder();
        buildAppLayout(appLayoutBuilder);
        appLayout = appLayoutBuilder.build();

        setContent(appLayout);
	}

	protected CDIAppLayoutBuilder createAppLayoutBuilder() {
		return AppLayout.getCDIBuilder(Behaviour.LEFT_OVERLAY)
	        .withViewProvider(() -> springViewProvider)
	        .withNavigationElementInfoProducer(new DefaultSpringNavigationElementInfoProducer())
	        .withTitle(i18n.get("Application.name"))
	        .withErrorView(() -> new ErrorView());
	}

	abstract protected void buildAppLayout(CDIAppLayoutBuilder builder);

}
