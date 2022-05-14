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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import com.github.yuri0x7c1.bali.ui.i18n.I18N;
import com.github.yuri0x7c1.bali.util.TimePeriod;
import com.github.yuri0x7c1.bali.util.TimeUtil;
import com.vaadin.spring.annotation.SpringComponent;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 *
 * @author yuri0x7c1
 *
 */
@SpringComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ZonedDateTimeRangeField extends AbstractDateTimeRangeField<ZonedDateTime> {
	@NonNull
	@Getter
	@Setter
	private ZoneId zoneId = ZoneId.systemDefault();

	public ZonedDateTimeRangeField() {
		super();
	}

	@Autowired
	public ZonedDateTimeRangeField(I18N i18n) {
		super(i18n);
	}

	@Override
	protected List<ZonedDateTime> getTimePeriodValues(TimePeriod timePeriod) {
		return TimeUtil.getZonedDateTimePeriod(timePeriod);
	}

	@Override
	protected void doSetValue(List<ZonedDateTime> value) {
		startDateTimeField.setValue(null);
		endDateTimeField.setValue(null);
		if (CollectionUtils.isNotEmpty(value)) {
			if (value.size() > 0 && value.get(0) != null) startDateTimeField.setValue(value.get(0).toLocalDateTime());
			if (value.size() > 1 && value.get(1) != null) endDateTimeField.setValue(value.get(1).toLocalDateTime());
		}
	}

	@Override
	public List<ZonedDateTime> getValue() {
		ZonedDateTime startDateTime = null;
		ZonedDateTime endDateTime = null;
		if (startDateTimeField.getValue() != null) startDateTime = startDateTimeField.getValue().atZone(zoneId);
		if (endDateTimeField.getValue() != null) endDateTime = endDateTimeField.getValue().atZone(zoneId);
		return Collections.unmodifiableList(Arrays.asList(startDateTime, endDateTime));
	}
}
