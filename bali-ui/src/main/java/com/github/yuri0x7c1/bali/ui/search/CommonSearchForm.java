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

package com.github.yuri0x7c1.bali.ui.search;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MCheckBox;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import org.vaadin.viritin.layouts.MWindow;

import com.github.yuri0x7c1.bali.data.search.model.SearchField;
import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.github.yuri0x7c1.bali.data.search.model.SearchModel;
import com.github.yuri0x7c1.bali.data.search.model.SearchModel.LogicalOperator;
import com.github.yuri0x7c1.bali.ui.card.Card;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 */
@Slf4j
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CommonSearchForm extends Card {

	private static final int DEFAULT_FIELD_LIMIT = 20;

	public enum SearchMode {
		SIMPLE,
		ADVANCED
	}

	private final ApplicationContext ctx;

	private final I18N i18n;

	private Map<String, SearchFieldComponentDescriptor> fieldDescriptors = new TreeMap<>();

	private List<SearchFieldComponent> fieldComponents = new LinkedList<>();

	private MCssLayout fieldLayout = new MCssLayout();

	private ComboBox<SearchFieldComponentDescriptor> fieldSelect;

	private ComboBox<SearchFieldOperator> operatorSelect;

	private MButton deleteFieldsButton;

	private MButton addFieldButton;

	private MWindow addFieldWindow;

	private MButton searchButton;

	private MButton clearAllButton;

	private SearchMode searchMode = SearchMode.SIMPLE;

	private MCheckBox searchModeCheckBox;

	private RadioButtonGroup<LogicalOperator> logicalOperators;

	@Setter
	Runnable searchHandler;

	public CommonSearchForm(ApplicationContext ctx, I18N i18n) {
		super();
		this.ctx = ctx;
		this.i18n = i18n;

		setHeaderText(i18n.get("Search.searchParams"));
		setHeaderMargin(true);
		setWidthFull();
		addStyleName(BaliStyle.COMMON_SEARCH_FORM);

		// initialize search button
		searchButton = new MButton(i18n.get("Search"), e -> {
			if (searchHandler != null) {
				searchHandler.run();
			}
		})
		.withStyleName(ValoTheme.BUTTON_PRIMARY)
		.withIcon(VaadinIcons.SEARCH)
		.withClickShortcut(KeyCode.ENTER);

		// initialize operator select
		operatorSelect = new ComboBox<SearchFieldOperator>();
		operatorSelect.setItemCaptionGenerator(
				item -> i18n.get(SearchFieldOperator.class.getSimpleName() + "." + item.name()));
		operatorSelect.setWidthFull();

		// initialize field select
		fieldSelect = new ComboBox<>(i18n.get("Search.fieldName"));
		fieldSelect.setWidthFull();
		fieldSelect.setItemCaptionGenerator(fc -> fc.getCaption());
		fieldSelect.setEmptySelectionAllowed(false);
		fieldSelect.addValueChangeListener(event -> {
			Set<SearchFieldOperator> validOperators = event.getValue().getValidOperators();

			// TODO: support spec operator with name
			if (validOperators.contains(SearchFieldOperator.SPEC)) {
				validOperators.remove(SearchFieldOperator.SPEC);
			}

			operatorSelect.setItems(validOperators);
			operatorSelect.setSelectedItem(event.getValue().getValidOperators().iterator().next());
		});

		// initialize delete fields button
		deleteFieldsButton = new MButton(VaadinIcons.TRASH, i18n.get("Search.deleteFields"), e -> clearFieldComponents())
				.withVisible(SearchMode.ADVANCED.equals(searchMode));

		// initialize add field window
		addFieldWindow = new MWindow(i18n.get("Search.addField"),
			new MVerticalLayout(
				fieldSelect,
				operatorSelect,
				new MHorizontalLayout(
					new MButton(VaadinIcons.CHECK, i18n.get("Add"), event -> {
						SearchFieldComponentDescriptor d = fieldSelect.getValue();
						if (d != null) {
							createFieldComponent(d, operatorSelect.getValue(), new Object[0], null);
						}

						addFieldWindow.close();
					})
					.withStyleName(ValoTheme.BUTTON_PRIMARY),
					new MButton(VaadinIcons.CLOSE, i18n.get("Cancel"), event -> addFieldWindow.close())
				)
			)
			.withMargin(true)
		)
		.withModal(true)
		.withWidth(256, Unit.PIXELS)
		.withCenter();

		// initialize add field button
		addFieldButton = new MButton(i18n.get("Search.addField"), event -> {
			if (!UI.getCurrent().getWindows().contains(addFieldWindow)) {
				UI.getCurrent().addWindow(addFieldWindow);
				if (!MapUtils.isEmpty(fieldDescriptors)) {
					fieldSelect.setValue(fieldDescriptors.values().iterator().next());
				}
			}
		})
		.withIcon(VaadinIcons.PLUS)
		.withVisible(SearchMode.ADVANCED.equals(searchMode));

		// initialize clear all button
		clearAllButton = new MButton(VaadinIcons.CLOSE, i18n.get("Search.clearAll"), e -> {
			for (SearchFieldComponent c : fieldComponents) {
				c.clearValue();
			}
		});

		// initialize search mode checkbox
		searchModeCheckBox = new MCheckBox(i18n.get("Search.advanced"), SearchMode.ADVANCED.equals(searchMode));
		searchModeCheckBox.addValueChangeListener(event -> {
			if (event.getValue()) {
				searchMode = SearchMode.ADVANCED;
			}
			else {
				searchMode = SearchMode.SIMPLE;
			}
			updateSearchMode();
		});

		logicalOperators = new RadioButtonGroup<>();
		logicalOperators.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
		logicalOperators.addStyleName(ValoTheme.OPTIONGROUP_SMALL);
		logicalOperators.setItems(LogicalOperator.AND, LogicalOperator.OR);
		logicalOperators.setValue(LogicalOperator.AND);
		logicalOperators.setItemCaptionGenerator(o -> {
			if (LogicalOperator.AND.equals(o)) {
				return i18n.get("LogicalOperator.AND");
			}
			else {
				return i18n.get("LogicalOperator.OR");
			}
		});
		logicalOperators.setVisible(false);

		addHeaderComponent(new MHorizontalLayout(clearAllButton, searchModeCheckBox));
		setContent(new MVerticalLayout(logicalOperators, fieldLayout, new MHorizontalLayout(searchButton, deleteFieldsButton, addFieldButton)));

	}

	private void updateSearchMode() {
		if (SearchMode.SIMPLE.equals(searchMode)) {
			deleteFieldsButton.setVisible(false);
			addFieldButton.setVisible(false);
			logicalOperators.setVisible(false);
		}
		else if (SearchMode.ADVANCED.equals(searchMode)) {
			deleteFieldsButton.setVisible(true);
			addFieldButton.setVisible(true);
			logicalOperators.setVisible(true);
		}
		for (SearchFieldComponent fieldComponent : fieldComponents) {
			fieldComponent.setSearchMode(searchMode);
		}
	}

	public SearchModel getModel() {
		SearchModel searchModel = new SearchModel();
		for (SearchFieldComponent fieldComponent : fieldComponents) {
			boolean isBlank = true;
			if (fieldComponent.getValue() != null) {
				if (fieldComponent.getValue() instanceof String) {
					if (StringUtils.isNotBlank((String) fieldComponent.getValue())) {
						isBlank = false;
					}
				}
				else {
					isBlank = false;
				}
			}
			if (!isBlank) {
				SearchFieldComponentDescriptor d = fieldDescriptors.get(fieldComponent.getName());
				Object v = d.getConverter() == null ? fieldComponent.getValue()
						: d.getConverter().convertToPresentation(fieldComponent.getValue(), null);
				searchModel.getFields().add(new SearchField(fieldComponent.getName(), fieldComponent.getOperator(), fieldComponent.getParams(), v));
			}
		}

		searchModel.setLogicalOperator(getLogicalOperator());

		return searchModel;
	}

	private void createFieldComponent(SearchFieldComponentDescriptor descriptor, SearchFieldOperator operator, Object[] params, Object value) {
		Component component = null;
		try {
			SearchFieldComponentDescription description = descriptor.getComponentDescription(operator);

			if (description.getComponentSupplier() == null) {
				if (SearchFieldComponentLifecycle.NON_MANAGED.equals(description.getComponentLifecycle())) {
					component = description.getComponentClass().getDeclaredConstructor().newInstance();
				}
				else if (SearchFieldComponentLifecycle.MANAGED.equals(description.getComponentLifecycle())) {
					component = ctx.getBean(description.getComponentClass());
				}
			}
			else {
				component = description.getComponentSupplier().get();
			}
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return;
		}

		if (!(component instanceof HasValue)) {
			throw new RuntimeException("Search field component must implement HasValue interface!");
		}

		SearchFieldComponent fieldComponent = new SearchFieldComponent(i18n, descriptor.getFieldName(),
				descriptor.getCaption(), operator, params, component, searchMode);
		if (value != null) {
			fieldComponent.setValue(value);
		}

		fieldComponent.setCloseHandler(() -> {
			fieldComponents.remove(fieldComponent);
			fieldLayout.removeComponent(fieldComponent);
		});

		fieldComponents.add(
			fieldComponent
		);

		fieldLayout.add(fieldComponent);
	}

	public void createFieldComponent(String fieldName, SearchFieldOperator operator, Object[] params, Object value) {
		SearchFieldComponentDescriptor d = fieldDescriptors.get(fieldName);
		if (d == null) {
			throw new RuntimeException(String.format("Descriptor for search field %s not found!", fieldName));
		}
		createFieldComponent(d, operator, params, value);
	}

	public void createFieldComponent(String fieldName, SearchFieldOperator operator) {
		createFieldComponent(fieldName, operator, new Object[0], null);
	}

	public void clearFieldComponents() {
		fieldLayout.removeAllComponents();
		fieldComponents.clear();
	}

	public void registerFieldComponent(SearchFieldComponentDescriptor fieldComponent) {
		fieldDescriptors.put(fieldComponent.getFieldName(), fieldComponent);
		fieldSelect.setItems(fieldDescriptors.values());
	}

	public LogicalOperator getLogicalOperator() {
		return logicalOperators.getValue();
	}

	public void setLogicalOperator(LogicalOperator logicalOperator) {
		logicalOperators.setValue(logicalOperator);
	}
}
