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

package com.github.yuri0x7c1.bali.ui.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.github.yuri0x7c1.bali.ui.handler.AddHandler;
import com.github.yuri0x7c1.bali.ui.handler.CancelHandler;
import com.github.yuri0x7c1.bali.ui.handler.CloseHandler;
import com.github.yuri0x7c1.bali.ui.handler.ConfirmHandler;
import com.github.yuri0x7c1.bali.ui.handler.EditHandler;
import com.github.yuri0x7c1.bali.ui.handler.RemoveHandler;
import com.github.yuri0x7c1.bali.ui.picker.EntityPicker;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author yuri0x7c1
 *
 */
public abstract class EntityMultiComponent<T> extends CustomField<List<T>> {
	@Getter
	private final Class<T> entityType;

	@Getter
	private final I18N i18n;

	@Getter
	private MButton addButton;

	@Getter
	private ConfirmButton removeAllButton;

	@Getter
	private MGrid<T> valueGrid;

	@Getter
	private Window window;

	private List<T> value = Collections.emptyList();

	private final List<EntityProperty<T>> properties = new ArrayList<>();

	@Getter
	@Setter
	private Sort sort;

	@Getter
	@Setter
	private AddHandler<T> addHandler;

	@Getter
	@Setter
	private RemoveHandler<List<T>> removeAllHandler;

	@Getter
	@Setter
	private EditHandler<T> editHandler;

	@Getter
	@Setter
	private RemoveHandler<T> removeHandler;

	@Getter
	@Setter
	private CloseHandler closeHandler;

	@Getter
	@Setter
	private ConfirmHandler<T> confirmHandler;

	@Getter
	@Setter
	private CancelHandler<T> cancelHandler;

	@Getter
	@Setter
	private String addMessage;

	public EntityMultiComponent(Class<T> entityType, I18N i18n) {
		super();
		this.entityType = entityType;
		this.i18n = i18n;

		addMessage = i18n.get("Select");

		// window
		window = new Window();
        window.setModal(true);
        window.setWidth(EntityPicker.WINDOW_SIZE);
        window.setHeight(EntityPicker.WINDOW_SIZE);

		// value grid
		valueGrid = new MGrid<>(entityType);
		valueGrid.setSelectionMode(SelectionMode.NONE);

		// add action columns
    	Column<T, MHorizontalLayout> c = valueGrid.addComponentColumn(entity -> {
    		MHorizontalLayout l = new MHorizontalLayout().withFullWidth();

    		if (removeHandler != null) {
				MButton removeButton = new MButton(VaadinIcons.CLOSE, event -> {
					removeHandler.onRemove(entity);
				})
				.withDescription(i18n.get("Delete"))
				.withStyleName(
						BaliStyle.MULTI_EDITOR_ACTION_BUTTON,
						ValoTheme.BUTTON_SMALL
				);
				l.add(removeButton);
    		}

    		if (editHandler != null) {
				MButton editButton = new MButton(VaadinIcons.PENCIL, event -> {
					editHandler.onEdit(entity);
				})
				.withDescription(i18n.get("Edit"))
				.withStyleName(
						BaliStyle.MULTI_EDITOR_ACTION_BUTTON,
						ValoTheme.BUTTON_SMALL
				);
				l.add(editButton);
    		}

    		return l;
    	});
    	c.setId(BaliStyle.ACTIONS_COLUMN_ID);
		c.setWidth(112);
    	c.setSortable(false);
    	c.setResizable(false);

		// add button
		addButton = new MButton(VaadinIcons.PLUS, event -> {
			if (addHandler != null) {
				addHandler.onAdd();
			}
		})
		.withDescription(getAddMessage())
		.withStyleName(
			BaliStyle.MULTI_EDITOR_ACTION_BUTTON,
			ValoTheme.BUTTON_SMALL
		);

		// remove all button
		removeAllButton = new ConfirmButton()
			.addClickListener(() -> {
				removeAllHandler.onRemove(value);
			})
			.withIcon(VaadinIcons.CLOSE)
			.withDescription(i18n.get("Delete.all"))
			.setConfirmationText(i18n.get("Delete.all.confirm"))
			.withStyleName(
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON,
				ValoTheme.BUTTON_SMALL
			);

		// add buttons to header
		MHorizontalLayout buttonLayout = new MHorizontalLayout()
			.withFullWidth()
			.withMargin(false);

		buttonLayout.add(removeAllButton, addButton);
		valueGrid.getDefaultHeaderRow().getCell(BaliStyle.ACTIONS_COLUMN_ID).setComponent(buttonLayout);

		// init handlers
		removeAllHandler = initRemoveAllHandler();
		removeHandler = initRemoveHandler();

		// on attach
		addAttachListener(event -> {
			setProperties(initProperties());
			refreshColumns();
			if (closeHandler != null) {
				window.addCloseListener(closeEvent -> closeHandler.onClose());
			}
	        window.setContent(initWindowContent());
		});
	}


	protected RemoveHandler<List<T>> initRemoveAllHandler() {
		return (entities) -> setValue(Collections.emptyList());
	}

	protected RemoveHandler<T> initRemoveHandler() {
		return entity -> {
			List<T> newValue = new ArrayList<>();
			newValue.addAll(getValue());
			newValue.remove(entity);
			setValue(Collections.unmodifiableList(newValue));
		};
	}

	abstract protected List<EntityProperty<T>> initProperties();

	abstract protected Component initWindowContent();

	@Override
	protected Component initContent() {
		return valueGrid;
	}

	@Override
	protected void doSetValue(List<T> value) {
		if (value == null) value = Collections.emptyList();
		this.value = value;
		valueGrid.setItems(value);
	}

	@Override
	public List<T> getValue() {
		return value;
	}

	public List<EntityProperty<T>> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	public void setProperties(List<EntityProperty<T>> properties) {
		this.properties.clear();
		this.properties.addAll(properties);
	}

	public void addProperty(EntityProperty<T> property) {
		properties.add(property);
	}

	public void addProperties(List<EntityProperty<T>> properties) {
		for (EntityProperty<T> p : properties) {
			addProperty(p);
		}
	}

	public void refreshColumns() {
		// clear columns
        for (Column<T, ?> column : valueGrid.getColumns()) {
        	if (!BaliStyle.ACTIONS_COLUMN_ID.equals(column.getId())) {
        		valueGrid.removeColumn(column);
        	}
        }

		// property columns
		for (EntityProperty<T> property : properties) {
			if (property.getValueProvider() == null) {
				valueGrid.addColumn(property.getName()).setCaption(property.getCaption()).setSortable(property.isSortable());
			}
			else {
				valueGrid.addColumn(e -> property.getValueProvider().apply(e)).setId(property.getName())
						.setCaption(property.getCaption()).setSortable(property.isSortable());
			}
		}

		// sort grid
		if (sort != null) {
			valueGrid.setSortOrder(UiUtil.convertSort(valueGrid, sort));
		}
	}

	public EntityProperty.Builder<T> propertyBuilder() {
		return EntityProperty.<T>builder();
	}
}
