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
import org.vaadin.viritin.layouts.MHorizontalLayout;

import com.github.yuri0x7c1.bali.ui.i18n.Formatter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.DateTimeField;

/**
 *
 * @author yuri0x7c1
 *
 */
@SpringComponent
public class DateTimeRangeField extends CustomField<List<LocalDateTime>> {
	private final DateTimeField startDateTimeField = new DateTimeField();
	private final DateTimeField endDateTimeField = new DateTimeField();

	public DateTimeRangeField(Formatter f) {
		startDateTimeField.setDateFormat(f.getDateTimeFormatPattern());
		startDateTimeField.setWidth(140, Unit.PIXELS);
		startDateTimeField.setPlaceholder(f.get("startDate"));

		endDateTimeField.setDateFormat(f.getDateTimeFormatPattern());
		endDateTimeField.setWidth(140, Unit.PIXELS);
		endDateTimeField.setPlaceholder(f.get("endDate"));
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
