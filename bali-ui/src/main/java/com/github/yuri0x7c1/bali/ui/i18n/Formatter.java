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

package com.github.yuri0x7c1.bali.ui.i18n;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.ApplicationContext;
import org.vaadin.spring.i18n.I18N;

/**
 *
 * @author yuri0x7c1
 *
 */
public class Formatter extends I18N {
	public static final String DEFAULT_DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_FORMAT_PATTERN = "HH:mm:ss";

	public static final String DATETIME_FORMAT_PROPERTY = "dateTime.format";
	public static final String DATE_FORMAT_PROPERTY = "date.format";
	public static final String TIME_FORMAT_PROPERTY = "time.format";

	private final DateTimeFormatter dateTimeFormatPattern;
	private final DateTimeFormatter dateFormatter;
	private final DateTimeFormatter timeFormatter;

	ApplicationContext applicationContext;
	public Formatter(ApplicationContext applicationContext) {
		super(applicationContext);
		this.applicationContext = applicationContext;

		dateTimeFormatPattern = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT_PATTERN);
		dateFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT_PATTERN);
		timeFormatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT_PATTERN);
	}

	public String getDateTimeFormatPattern() {
		return DEFAULT_DATETIME_FORMAT_PATTERN;
	}

	public String getDateFormatPattern() {
		return DEFAULT_DATE_FORMAT_PATTERN;
	}

	public String format(LocalDateTime localDateTime) {
		if (localDateTime == null) return "";
		return dateTimeFormatPattern.format(localDateTime);
	}

	public String format(LocalDate localDate) {
		if (localDate == null) return "";
		return dateFormatter.format(localDate);
	}

	public String format(LocalTime localTime) {
		if (localTime == null) return "";
		return timeFormatter.format(localTime);
	}
}
