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

package com.github.yuri0x7c1.bali.demo.ui.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.events.EventBus.UIEventBus;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import com.github.yuri0x7c1.bali.demo.service.FooService;
import com.github.yuri0x7c1.bali.demo.ui.form.FooForm;
import com.github.yuri0x7c1.bali.security.util.SecurityUtil;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FooMultiEditor extends CustomField<List<Foo>>{
	final I18N i18n;

	final UIEventBus eventBus;

	final FooService fooService;

	final FooForm fooForm;

	MButton createButton;

	MButton editButton;

	ConfirmButton deleteButton;

	MGrid<Foo> valueGrid;

	Window window;

	MButton confirmButton;

	MButton cancelButton;

	List<Foo> value;

	@PostConstruct
	public void init() {
		addStyleName(BaliStyle.MULTI_EDITOR);

		// window
		window = new Window();
        window.setCaption("Foo.create");
        window.setModal(true);
        window.setWidth("80%");
        window.setHeight("80%");

		// value grid
		valueGrid = new MGrid<>(Foo.class);

		// create button
		if (SecurityUtil.check("ROLE_CONTENT_CREATE")) {
			createButton = new MButton(VaadinIcons.PLUS)
				.withDescription(i18n.get("Foo.create"))
				.withStyleName(
					ValoTheme.BUTTON_ICON_ONLY,
					BaliStyle.MULTI_EDITOR_ACTION_BUTTON
				)
				.withListener(e -> {
					fooForm.setEntity(new Foo());
					window.setCaption(i18n.get("Foo.create"));
					getUI().addWindow(window);
				});
		}

		// edit button
		if (SecurityUtil.check("ROLE_CONTENT_EDIT")) {
			editButton = new MButton(VaadinIcons.PENCIL)
				.withDescription(i18n.get("Foo.edit"))
				.withStyleName(
					ValoTheme.BUTTON_ICON_ONLY,
					BaliStyle.MULTI_EDITOR_ACTION_BUTTON
				)
				.withListener(e -> {
					Set<Foo> selected = valueGrid.getSelectedItems();
					if (CollectionUtils.isNotEmpty(selected)) {
						if (selected.size() == 1) {
							fooForm.setEntity(selected.iterator().next());
							window.setCaption(i18n.get("Foo.edit"));
							getUI().addWindow(window);
						}
					}
				});
		}

		// delete button
		if (SecurityUtil.check("ROLE_CONTENT_DELETE")) {
			deleteButton = new ConfirmButton()
				.withIcon(VaadinIcons.TRASH)
				.withDescription(i18n.get("Foo.delete"))
				.setConfirmationText(i18n.get("Delete.confirm"))
				.withStyleName(
					ValoTheme.BUTTON_ICON_ONLY,
					ValoTheme.BUTTON_DANGER,
					BaliStyle.MULTI_EDITOR_ACTION_BUTTON
				)
				.addClickListener(() -> {
					Set<Foo> selected = valueGrid.getSelectedItems();
					if (CollectionUtils.isNotEmpty(selected)) {
						if (selected.size() == 1) {
							List<Foo> newValue = new ArrayList<>();
							newValue.addAll(getValue());
							newValue.remove(selected.iterator().next());
							setValue(Collections.unmodifiableList(newValue));
						}
					}
				});
		}

		// confirm button
		confirmButton = new MButton(i18n.get("Confirm"))
			.withStyleName(ValoTheme.BUTTON_PRIMARY)
			.withListener(e -> {
				window.close();
				List<Foo> newValue = new ArrayList<>(getValue());
				newValue.add(fooForm.getEntity());
				setValue(Collections.unmodifiableList(newValue));
				fooForm.setEntity(null);
			});

		// cancel button
		cancelButton = new MButton(i18n.get("Cancel"))
			.withStyleName(ValoTheme.BUTTON_DANGER)
			.withListener(e -> {
				window.close();
				fooForm.setEntity(null);
			});

		// set window content
        window.setContent(new MVerticalLayout(
        	fooForm,
        	new MHorizontalLayout(confirmButton, cancelButton))
        );
	}

	@Override
	protected Component initContent() {
		return new MCssLayout(
			new MCssLayout(createButton, editButton, deleteButton),
			valueGrid
		);
	}

	@Override
	protected void doSetValue(List<Foo> value) {
		this.value = value;
		valueGrid.setItems(value);
	}

	@Override
	public List<Foo> getValue() {
		return value;
	}

	public FooMultiEditor withCaption(String caption) {
		setCaption(caption);
		return this;
	}

	public FooMultiEditor clearColumns() {
		valueGrid.setColumns(new String[]{});
		return this;
	}

	public FooMultiEditor withColumn(String propertyName, String caption) {
		valueGrid.addColumn(propertyName).setCaption(caption);
		return this;
	}

	public FooMultiEditor withColumn(ValueProvider<Foo, String> valueProvider, String caption) {
		valueGrid.addColumn(valueProvider).setCaption(caption);
		return this;
	}


	public FooMultiEditor withHeaderVisble(boolean headerVisible) {
		valueGrid.setHeaderVisible(headerVisible);
		return this;
	}
}
