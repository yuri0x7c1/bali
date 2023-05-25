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

package com.github.yuri0x7c1.bali.ui.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.grid.MGrid;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.github.yuri0x7c1.bali.ui.form.EntityForm;
import com.github.yuri0x7c1.bali.ui.form.EntityForm.FormAction;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.github.yuri0x7c1.bali.ui.util.UiUtil;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 */
@Slf4j
public class EntityMultiEditor<T> extends CustomField<List<T>> {
	private final Class<T> entityType;

	private final I18N i18n;

	private final AbstractForm<T> entityForm;

	@Setter
	private Supplier<T> entitySupplier;

	private MButton createButton;

	private MButton editButton;

	private ConfirmButton deleteButton;

	private MGrid<T> valueGrid;

	private Window window;

	private List<T> value = Collections.emptyList();

	private final List<EntityProperty<T>> properties = new ArrayList<>();

	@Getter
	@Setter
	private String sortProperty;

	@Getter
	@Setter
	private Direction sortDirection = Direction.ASC;

	public EntityMultiEditor(Class<T> entityType, I18N i18n, AbstractForm<T> entityForm) {
		super();
		this.entityType = entityType;
		this.i18n = i18n;
		this.entityForm = entityForm;

		addStyleName(BaliStyle.MULTI_EDITOR);

		// default entity supplier
		setEntitySupplier(() -> {
			try {
				return entityType.newInstance();
			}
			catch (Exception ex) {
				log.error(ex.getMessage(), ex);
				return null;
			}
		});

		// window
		window = new Window();
        window.setModal(true);
        window.setWidth("80%");
        window.setHeight("80%");

		// value grid
		valueGrid = new MGrid<>(entityType);

		// create button
		createButton = new MButton(VaadinIcons.PLUS)
			.withDescription(i18n.get("Create"))
			.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			)
			.withListener(e -> {
				if (entityForm instanceof EntityForm) {
					((EntityForm<T>) entityForm).setFormAction(FormAction.CREATE);
				}
				entityForm.setEntity(entitySupplier.get());
				window.setCaption(i18n.get("Create"));
				getUI().addWindow(window);
			});

		// edit button
		editButton = new MButton(VaadinIcons.PENCIL)
			.withDescription(i18n.get("Edit"))
			.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			)
			.withListener(e -> {
				Set<T> selected = valueGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						if (entityForm instanceof EntityForm) {
							((EntityForm<T>) entityForm).setFormAction(FormAction.EDIT);
						}
						entityForm.setEntity(selected.iterator().next());
						window.setCaption(i18n.get("Edit"));
						getUI().addWindow(window);
					}
				}
			});

		// delete button
		deleteButton = new ConfirmButton()
			.withIcon(VaadinIcons.TRASH)
			.withDescription(i18n.get("Delete"))
			.setConfirmationText(i18n.get("Delete.confirm"))
			.withStyleName(
				ValoTheme.BUTTON_ICON_ONLY,
				ValoTheme.BUTTON_DANGER,
				BaliStyle.MULTI_EDITOR_ACTION_BUTTON
			)
			.addClickListener(() -> {
				Set<T> selected = valueGrid.getSelectedItems();
				if (CollectionUtils.isNotEmpty(selected)) {
					if (selected.size() == 1) {
						List<T> newValue = new ArrayList<>();
						newValue.addAll(getValue());
						newValue.remove(selected.iterator().next());
						setValue(Collections.unmodifiableList(newValue));
					}
				}
			});

		// confirm button
		entityForm.setSavedHandler(entity -> {
			window.close();
			List<T> newValue = new ArrayList<>(getValue());
			newValue.add(entityForm.getEntity());
			setValue(Collections.unmodifiableList(newValue));
			entityForm.setEntity(null);
		});

		// cancel button
		entityForm.setResetHandler(entity -> {
			window.close();
			entityForm.setEntity(null);
		});

		// set window content
        window.setContent(new MVerticalLayout(
        	entityForm
        ));
	}

	@Override
	protected Component initContent() {
		return new MCssLayout(
			new MCssLayout(createButton, editButton, deleteButton),
			valueGrid
		);
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
    		valueGrid.removeColumn(column);
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
		valueGrid.sort(sortProperty, UiUtil.convertDirection(sortDirection));
	}

	public EntityProperty.Builder<T> propertyBuilder() {
		return EntityProperty.<T>builder();
	}
}
