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

package com.github.yuri0x7c1.bali.ui.security;

import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * UI for the login screen.
 *
 * @author Petter Holmström (petter@vaadin.com)
 * @author yuri0x7c1
 */
@Slf4j
@RequiredArgsConstructor
public class SharedLoginUI extends UI {

	@NonNull
	private I18N i18n;

    @NonNull
    private VaadinSharedSecurity vaadinSecurity;

    @Getter
    private TextField usernameField;

    @Getter
    private PasswordField passwordField;

    @Getter
    private CheckBox rememberMeField;

    @Getter
    private Button loginButton;

    private Label loginFailedLabel;

    private Label loggedOutLabel;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle(i18n.get("Application.name"));

        Component loginForm = buildLoginForm(request);
        setContent(
        	new MVerticalLayout(
        		loginForm
        	)
        	.withFullSize()
        	.withMargin(true)
        	.withSpacing(false)
        	.withComponentAlignment(loginForm, Alignment.MIDDLE_CENTER)
        );
    }

    private Component buildLoginForm(VaadinRequest request) {
        final VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.addStyleName(ValoTheme.LAYOUT_CARD);
        loginLayout.setSizeUndefined();
        loginLayout.setMargin(true);
        loginLayout.addStyleName("bali-login-panel");

        if (request.getParameter("logout") != null) {
            loggedOutLabel = new Label(i18n.get("Login.logoutMsg"));
            loggedOutLabel.addStyleName(ValoTheme.LABEL_SUCCESS);
            loggedOutLabel.setWidthFull();
            loginLayout.addComponent(loggedOutLabel);
            loginLayout.setComponentAlignment(loggedOutLabel, Alignment.BOTTOM_CENTER);
        }

        loginLayout.addComponent(loginFailedLabel = new Label());
        loginLayout.setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER);
        loginFailedLabel.setWidthFull();
        loginFailedLabel.addStyleName(ValoTheme.LABEL_FAILURE);
        loginFailedLabel.setVisible(false);


        loginLayout.addComponent(buildLabels());
        loginLayout.addComponent(buildFields());
        rememberMeField = new CheckBox(i18n.get("Login.rememberMe"), false);
        loginLayout.addComponent(rememberMeField);
        return loginLayout;
    }

    private Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.addStyleName("fields");

        usernameField = new TextField(i18n.get("Login.username"));
        usernameField.setIcon(FontAwesome.USER);
        usernameField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        passwordField = new PasswordField(i18n.get("Login.password"));
        passwordField.setIcon(FontAwesome.LOCK);
        passwordField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        loginButton = new Button(i18n.get("Login.signIn"));
        loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        loginButton.setClickShortcut(KeyCode.ENTER);
        loginButton.focus();

        fields.addComponents(usernameField, passwordField, loginButton);
        fields.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);

        loginButton.addClickListener(event -> login());
        return fields;
    }

    private Component buildLabels() {
        CssLayout labels = new CssLayout();
        Label title = new Label(i18n.get("Application.name"));
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
        return labels;
    }

    private void login() {
        try {
            vaadinSecurity.login(usernameField.getValue(), passwordField.getValue(), rememberMeField.getValue());
        } catch (AuthenticationException ex) {
            usernameField.focus();
            usernameField.selectAll();
            passwordField.setValue("");
            loginFailedLabel.setValue(i18n.get("Login.failedMsg", ex.getMessage()));
            loginFailedLabel.setVisible(true);
            if (loggedOutLabel != null) {
                loggedOutLabel.setVisible(false);
            }
        } catch (Exception ex) {
            Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
            log.error("Unexpected error while logging in", ex);
        } finally {
            loginButton.setEnabled(true);
        }
    }
}