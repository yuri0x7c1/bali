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

package com.github.yuri0x7c1.bali.ui.field;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.layouts.MCssLayout;

import com.github.yuri0x7c1.bali.context.ApplicationContextProvider;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.ui.style.BaliStyle;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

import lombok.Getter;

/**
 *
 * @author yuri0x7c1
 *
 */
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LocalDateRangeField extends CustomField<List<LocalDate>> {
	private final I18N i18n;

	@Getter
	private final LocalDateField startDateField;

	@Getter
	private final LocalDateField endDateField;

	public LocalDateRangeField() {
		this(ApplicationContextProvider.getContext().getBean(I18N.class));
	}

	@Autowired
	public LocalDateRangeField(I18N i18n) {
		this.i18n = i18n;

		startDateField = new LocalDateField(i18n);
		startDateField.setWidth(124, Unit.PIXELS);
		startDateField.setPlaceholder(i18n.get("From"));
		startDateField.addStyleName(AbstractDateTimeRangeField.START_CSS_CLASS_NAME);

		endDateField = new LocalDateField(i18n);
		endDateField.setWidth(124, Unit.PIXELS);
		endDateField.setPlaceholder(i18n.get("To"));
		endDateField.addStyleName(AbstractDateTimeRangeField.END_CSS_CLASS_NAME);

		addStyleName(BaliStyle.TEMPORAL_RANGE_FIELD);
		setWidthUndefined();
	}

	@Override
	protected Component initContent() {
		return new MCssLayout(startDateField, endDateField);
	}

	@Override
	protected void doSetValue(List<LocalDate> value) {
		startDateField.setValue(null);
		endDateField.setValue(null);
		if (CollectionUtils.isNotEmpty(value)) {
			if (value.size() > 0) startDateField.setValue(value.get(0));
			if (value.size() > 1) endDateField.setValue(value.get(1));
		}
	}

	@Override
	public List<LocalDate> getValue() {
		return Collections.unmodifiableList(Arrays.asList(startDateField.getValue(), endDateField.getValue()));
	}
}
