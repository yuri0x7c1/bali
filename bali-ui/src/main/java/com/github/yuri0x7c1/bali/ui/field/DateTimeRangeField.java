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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.github.yuri0x7c1.bali.context.ApplicationContextProvider;
import com.github.yuri0x7c1.bali.ui.i18n.Formatter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;

/**
 *
 * @author yuri0x7c1
 *
 */
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DateTimeRangeField extends CustomField<List<LocalDateTime>> {
	private final Formatter f;
	private final DateTimeField startDateTimeField;
	private final DateTimeField endDateTimeField;

	public DateTimeRangeField() {
		this(ApplicationContextProvider.getContext().getBean(Formatter.class));
	}

	@Autowired
	public DateTimeRangeField(Formatter f) {
		this.f = f;

		startDateTimeField = new DateTimeField(f);
		startDateTimeField.setWidth(190, Unit.PIXELS);
		startDateTimeField.setPlaceholder(f.get("startDateTime"));

		endDateTimeField = new DateTimeField(f);
		endDateTimeField.setWidth(190, Unit.PIXELS);
		endDateTimeField.setPlaceholder(f.get("endDateTime"));
	}

	@Override
	protected Component initContent() {
		return new MHorizontalLayout(startDateTimeField, endDateTimeField);
	}

	@Override
	protected void doSetValue(List<LocalDateTime> value) {
		startDateTimeField.setValue(null);
		endDateTimeField.setValue(null);
		if (CollectionUtils.isNotEmpty(value)) {
			if (value.size() > 0) startDateTimeField.setValue(value.get(0));
			if (value.size() > 1) endDateTimeField.setValue(value.get(1));
		}
	}

	@Override
	public List<LocalDateTime> getValue() {
		return Collections.unmodifiableList(Arrays.asList(startDateTimeField.getValue(), endDateTimeField.getValue()));
	}
}
