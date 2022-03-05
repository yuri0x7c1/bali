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
import java.util.function.Supplier;

import org.vaadin.spring.i18n.I18N;

import com.github.appreciated.app.layout.AppLayout;
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
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PushStateNavigation
@Viewport("width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no")
@StyleSheet("vaadin://bali.css")
@Theme("bali_default")
@Widgetset("BaliDefaultWidgetset")
public abstract class CommonUI extends UI {

	@Getter
	private final I18N i18n;

    private final SpringViewProvider springViewProvider;

    @NonNull
    @Getter
    @Setter
    private Supplier<String> pageTitleSupplier;

    @NonNull
    @Getter
    @Setter
    private Supplier<String> appLayoutTitleSupplier;

	public CommonUI(I18N i18n, SpringViewProvider springViewProvider) {
		super();
		this.i18n = i18n;
		this.springViewProvider = springViewProvider;

		pageTitleSupplier = () -> i18n.get("Application.name");
		appLayoutTitleSupplier = () -> i18n.get("Application.name");
	}

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
        getPage().setTitle(pageTitleSupplier.get());

        // build application layout
        CDIAppLayoutBuilder appLayoutBuilder = initAppLayoutBuilder();
        buildAppLayout(appLayoutBuilder);
        setContent(appLayoutBuilder.build());
	}

	protected CDIAppLayoutBuilder initAppLayoutBuilder() {
		return AppLayout.getCDIBuilder(Behaviour.LEFT_OVERLAY)
	        .withViewProvider(() -> springViewProvider)
	        .withNavigationElementInfoProducer(new DefaultSpringNavigationElementInfoProducer())
	        .withErrorView(() -> new ErrorView())
	        .withTitle(appLayoutTitleSupplier.get());
	}

	abstract protected void buildAppLayout(CDIAppLayoutBuilder builder);
}
