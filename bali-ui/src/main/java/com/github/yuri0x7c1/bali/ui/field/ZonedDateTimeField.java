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

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.github.yuri0x7c1.bali.context.ApplicationContextProvider;
import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.vaadin.shared.ui.datefield.DateTimeResolution;
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
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ZonedDateTimeField extends CustomField<ZonedDateTime> {
	private final I18N i18n;

	private final ZoneId zoneId;

	private final DateTimeField dateTimeField;

	private ZonedDateTime v;

	public ZonedDateTimeField() {
		this(ApplicationContextProvider.getContext().getBean(I18N.class), ZoneId.systemDefault());
	}

	public ZonedDateTimeField(String caption) {
		this(ApplicationContextProvider.getContext().getBean(I18N.class), ZoneId.systemDefault());
		setCaption(caption);
	}

	public ZonedDateTimeField(ZoneId zoneId) {
		this(ApplicationContextProvider.getContext().getBean(I18N.class), zoneId);
	}

	public ZonedDateTimeField(ZoneId zoneId, String caption) {
		this(ApplicationContextProvider.getContext().getBean(I18N.class), zoneId);
		setCaption(caption);
	}

	@Autowired
	public ZonedDateTimeField(I18N i18n, ZoneId zoneId) {
		this.i18n = i18n;
		this.zoneId = zoneId;
		this.dateTimeField = new DateTimeField();

		dateTimeField.setDateFormat(i18n.getDateTimeFormat());
		dateTimeField.setResolution(DateTimeResolution.SECOND);
		dateTimeField.addValueChangeListener(event -> {
			if (event.getValue() == null) {
				setValue(null);
			}
			else {
				setValue(event.getValue().atZone(zoneId));
			}
		});
	}

	@Override
	public ZonedDateTime getValue() {
		return v;
	}

	@Override
	protected Component initContent() {
		return dateTimeField;
	}

	@Override
	protected void doSetValue(ZonedDateTime value) {
		v = value;
		if (value == null) {
			dateTimeField.setValue(null);
		}
		else {
			dateTimeField.setValue(value.toLocalDateTime());
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		dateTimeField.setReadOnly(readOnly);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		dateTimeField.setEnabled(enabled);
	}
}
