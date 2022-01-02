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

import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.vaadin.ui.ComboBox;

/**
 *
 * @author yuri0x7c1
 *
 */
public class SearchFieldOperatorSelect extends ComboBox<SearchFieldOperator> {
	public SearchFieldOperatorSelect(I18N i18n) {
		setItemCaptionGenerator(item -> i18n.get(SearchFieldOperator.class.getSimpleName() + "." + item.name()));
		setItems(SearchFieldOperator.values());
	}
}
