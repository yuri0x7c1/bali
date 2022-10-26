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

import java.util.Collections;

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.github.yuri0x7c1.bali.ui.search.CommonSearchForm.SearchMode;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.AbstractMultiSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;

import lombok.Getter;

/**
 *
 * @author yuri0x7c1
 *
 */
public class SearchFieldComponent extends MCssLayout {
	public static final String FIELD_CSS_CLASS = "field";
	public static final String NAME_LABEL_CSS_CLASS = "label";

    private final I18N i18n;

    @Getter
    private final String name;

	private final MLabel nameLabel;

	@Getter
	private final SearchFieldOperator operator;

	@Getter
	private final Object[] params;

	private final MTextField operatorLabel;

	private final MTextField param1Label;

	private final Component valueComponent;

	private final MButton clearButton;

	private final MButton closeButton;

	@Getter
	private SearchMode searchMode;

	public SearchFieldComponent(I18N i18n, String name, String caption, SearchFieldOperator operator, Object[] params,
			Component valueComponent, SearchMode searchMode) {
		this.i18n = i18n;
		this.name = name;
		this.nameLabel = new MLabel(caption).withStyleName(NAME_LABEL_CSS_CLASS);
		this.operator = operator;
		this.params = params;

		this.operatorLabel = new MTextField().withReadOnly(true)
				.withValue(i18n.get(SearchFieldOperator.class.getSimpleName() + "." + operator.name()));
		this.param1Label = new MTextField().withReadOnly(true).withVisible(false);
		if (SearchFieldOperator.SPEC.equals(operator)) {
			param1Label.setValue(String.valueOf(params[0]));
			param1Label.setVisible(true);
		}
		this.valueComponent = valueComponent;
		this.clearButton = new MButton(VaadinIcons.CLOSE, event -> clearValue())
			.withStyleName(ValoTheme.BUTTON_BORDERLESS)
			.withDescription(i18n.get("Clear"));
		this.closeButton = new MButton(VaadinIcons.TRASH)
			.withStyleName(ValoTheme.BUTTON_BORDERLESS)
			.withDescription(i18n.get("Delete"));
		setSearchMode(searchMode);

		add(nameLabel, operatorLabel, param1Label, valueComponent, clearButton, closeButton);

		addStyleNames(ValoTheme.LAYOUT_CARD, FIELD_CSS_CLASS);
		setWidthUndefined();
	}

	public void setCloseHandler(Runnable closeHandler) {
		closeButton.addClickListener(event -> closeHandler.run());
	}

	public Object getValue() {
		return ((HasValue) valueComponent).getValue();
	}

	public void setValue (Object value) {
		((HasValue) valueComponent).setValue(value);
	}

	public void clearValue() {
		if (valueComponent != null) {
			if (valueComponent instanceof HasValue) {
				if (valueComponent instanceof AbstractTextField) {
					((AbstractTextField)valueComponent).setValue("");
				}
				else if (valueComponent instanceof AbstractMultiSelect) {
					((AbstractMultiSelect)valueComponent).setValue(Collections.EMPTY_SET);
				}
				else {
					((HasValue)valueComponent).setValue(null);
				}
			}
		}
	}

	public void setSearchMode(SearchMode searchMode) {
		this.searchMode = searchMode;
		if (SearchMode.SIMPLE.equals(searchMode)) {
			operatorLabel.setVisible(false);
			param1Label.setVisible(false);
			closeButton.setVisible(false);
		}
		else if (SearchMode.ADVANCED.equals(searchMode)) {
			operatorLabel.setVisible(true);
			if (SearchFieldOperator.SPEC.equals(operator)) {
				param1Label.setVisible(true);
			}
			closeButton.setVisible(true);
		}
	}
}
