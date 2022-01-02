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

package com.github.yuri0x7c1.bali.demo.ui.datagrid;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.vaadin.spring.i18n.I18N;
import org.vaadin.viritin.fields.IntegerField;
import org.vaadin.viritin.fields.MTextField;

import com.github.yuri0x7c1.bali.data.entity.EntityProperty;
import com.github.yuri0x7c1.bali.data.search.util.SearchUtil;
import com.github.yuri0x7c1.bali.demo.domain.Bar;
import com.github.yuri0x7c1.bali.demo.service.BarService;
import com.github.yuri0x7c1.bali.ui.datagrid.EntityDataGrid;
import com.github.yuri0x7c1.bali.ui.search.CommonSearchForm;
import com.github.yuri0x7c1.bali.ui.search.SearchFieldComponentDescriptor;
import com.github.yuri0x7c1.bali.ui.search.SearchFieldComponentLifecycle;
import com.vaadin.spring.annotation.SpringComponent;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BarDataGrid extends EntityDataGrid<Bar> {

	BarService barService;

	public BarDataGrid(I18N i18n, CommonSearchForm searchForm, BarService barService) {
		super(Bar.class, i18n, searchForm, Bar.Fields.id, Direction.ASC);
		this.barService = barService;

		setSearchProvider((page, pageSize, orderProperty, orderDirection, searchModel) -> barService.findAll(
			SearchUtil.buildSpecification(Bar.class, searchModel),
			PageRequest.of(
				page,
				pageSize,
				orderDirection,
				orderProperty
			)
		));

		setSearchCountProvider(searchModel -> barService.count(
			SearchUtil.buildSpecification(Bar.class, searchModel)
		));

		// search fields
		registerSearchFieldComponent(new SearchFieldComponentDescriptor("id", i18n.get("Bar.id"), Integer.class,
				IntegerField.class, SearchFieldComponentLifecycle.NON_MANAGED));
		registerSearchFieldComponent(new SearchFieldComponentDescriptor("value", i18n.get("Bar.value"), String.class,
				MTextField.class, SearchFieldComponentLifecycle.NON_MANAGED));

		// grid columns
		addProperty(new EntityProperty<>("id", i18n.get("Bar.id")));
		addProperty(new EntityProperty<>("value", i18n.get("Bar.value")));
		refreshColumns();
	}
}
