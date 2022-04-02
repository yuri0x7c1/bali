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

import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.MLabel;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MPanel;

import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.github.yuri0x7c1.bali.ui.search.CommonSearchForm.SearchMode;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;

import lombok.Getter;

/**
 *
 * @author yuri0x7c1
 *
 */
public class SearchFieldComponent extends MPanel {
    private final I18N i18n;

    @Getter
    private final String name;

	private final MHorizontalLayout layout;

	private final MLabel nameLabel;

	private final SearchFieldOperatorSelect optionSelect;

	private final Component valueComponent;

	private final MButton closeButton;
	
	@Getter
	private SearchMode searchMode;

	public SearchFieldComponent(I18N i18n, String name, String caption, SearchFieldOperator operator, Component valueComponent, SearchMode searchMode) {
		this.i18n = i18n;
		this.name = name;
		this.layout = new MHorizontalLayout()
			.withMargin(true);
		this.nameLabel = new MLabel(caption);
		this.optionSelect = new SearchFieldOperatorSelect(i18n);
		this.optionSelect.setValue(operator);
		this.valueComponent = valueComponent;
		this.closeButton = new MButton(VaadinIcons.CLOSE);
		setSearchMode(searchMode);

		layout.add(nameLabel, optionSelect, valueComponent, closeButton);

		addStyleName(BaliStyle.COMMON_SEARCH_FORM_FIELD);
		setWidthUndefined();
		setContent(layout);
	}

	public void setCloseHandler(Runnable closeHandler) {
		closeButton.addClickListener(event -> closeHandler.run());
	}

	public SearchFieldOperator getOperator() {
		return optionSelect.getValue();
	}

	public Object getValue() {
		return ((HasValue) valueComponent).getValue();
	}

	public void setValue (Object value) {
		((HasValue) valueComponent).setValue(value);
	}
	
	public void setSearchMode(SearchMode searchMode) {
		this.searchMode = searchMode;
		if (SearchMode.SIMPLE.equals(searchMode)) {
			optionSelect.setVisible(false);
			closeButton.setVisible(false);
		}
		else if (SearchMode.ADVANCED.equals(searchMode)) {
			optionSelect.setVisible(true);
			closeButton.setVisible(true);
		}
	}
}
