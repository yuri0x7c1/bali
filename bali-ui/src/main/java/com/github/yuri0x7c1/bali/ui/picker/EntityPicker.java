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

package com.github.yuri0x7c1.bali.ui.picker;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import org.vaadin.viritin.layouts.MWindow;

import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.themes.ValoTheme;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/**
 * Entity picker
 *
 * @param <T> entity type
 *
 * @author yuri0x7c1
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class EntityPicker<T> extends CustomField<T> {
	public static final String WINDOW_SIZE = "90%";

	Class<T> entityType;

	I18N i18n;

	EntityDataGrid<T> dataGrid;

	MTextField valueLabel;

	MWindow dialog;

	MButton selectButton;

	MButton clearButton;

	MButton confirmButton;

	MButton cancelButton;

	@NonFinal T value;

	@Setter
	@NonFinal ItemCaptionGenerator<T> valueCaptionGenerator;

	public EntityPicker(Class<T> entityType, I18N i18n, EntityDataGrid<T> dataGrid) {
		this.entityType = entityType;
		this.i18n = i18n;
		this.dataGrid = dataGrid;

		// window
		dialog = new MWindow();
		dialog.setCaption(i18n.get("Select"));
        dialog.setModal(true);
        dialog.setWidth(WINDOW_SIZE);
        dialog.setHeight(WINDOW_SIZE);

  		// grid
		dataGrid.setSelectionMode(SelectionMode.SINGLE);

		// value grid
		valueLabel = new MTextField().withReadOnly(true)
				.withStyleName(ValoTheme.TEXTFIELD_TINY, ValoTheme.TEXTFIELD_BORDERLESS);

		// select button
		selectButton = new MButton()
			.withIcon(VaadinIcons.ANGLE_DOWN)
			.withListener(e -> {
				if (getValue() != null) {
					dataGrid.setSelectedItem(getValue());
				}
				getUI().addWindow(dialog);
			})
			.withStyleName(ValoTheme.BUTTON_TINY, ValoTheme.BUTTON_BORDERLESS, BaliStyle.BORDERLESS_BUTTON_LIGHT);

		// delete button
		clearButton = new MButton(VaadinIcons.CLOSE, event -> {
				setValue(null);
		})
		.withStyleName(ValoTheme.BUTTON_TINY, ValoTheme.BUTTON_BORDERLESS, BaliStyle.BORDERLESS_BUTTON_LIGHT);

		// confirm button
		confirmButton = new MButton("Confirm")
			.withListener(e -> {
				dialog.close();
				setValue(dataGrid.getFirstSelectedItem().orElse(null));
			})
			.withStyleName(ValoTheme.BUTTON_PRIMARY);

		// cancel button
		cancelButton = new MButton("Cancel")
			.withListener(e -> {
				dialog.close();
			})
			.withStyleName(ValoTheme.BUTTON_DANGER);

		// set dialog content
		dialog.setContent(new MVerticalLayout(
        	dataGrid,
        	new MHorizontalLayout(confirmButton, cancelButton))
        );

		dataGrid.sortAndRefresh();
	}

	@Override
	protected Component initContent() {
		return new MHorizontalLayout(valueLabel, selectButton, clearButton)
			.withFullWidth()
			.withExpand(valueLabel, 1.0f)
			.withSpacing(false)
			.withStyleName("v-textfield")
			.withHeight(32, Unit.PIXELS);
	}

	@Override
	protected void doSetValue(T value) {
		this.value = value;

		if (value == null) {
			valueLabel.setValue("");
			return;
		}

		if (valueCaptionGenerator == null) {
			valueLabel.setValue(String.valueOf(value));
		}
		else {
			valueLabel.setValue(valueCaptionGenerator.apply(value));
		}
	}

	@Override
	public T getValue() {
		return value;
	}
}