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

import lombok.Getter;

/**
 *
 * @author yuri0x7c1
 *
 */
public class I18N extends org.vaadin.spring.i18n.I18N {
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

	public static final String DATETIME_FORMAT_PROPERTY = "dateTime.format";
	public static final String DATE_FORMAT_PROPERTY = "date.format";
	public static final String TIME_FORMAT_PROPERTY = "time.format";

	@Getter
	private String dateTimeFormat;

	@Getter
	private String dateFormat;

	@Getter
	private String timeFormat;

	private final DateTimeFormatter dateTimeFormatter;
	private final DateTimeFormatter dateFormatter;
	private final DateTimeFormatter timeFormatter;

	ApplicationContext ctx;
	public I18N(ApplicationContext ctx) {
		super(ctx);
		this.ctx = ctx;

		dateTimeFormat = ctx.getEnvironment().getProperty(DATETIME_FORMAT_PROPERTY.toLowerCase(), DEFAULT_DATETIME_FORMAT);
		dateFormat = ctx.getEnvironment().getProperty(DATE_FORMAT_PROPERTY, DEFAULT_DATE_FORMAT);
		timeFormat = ctx.getEnvironment().getProperty(TIME_FORMAT_PROPERTY, DEFAULT_TIME_FORMAT);

		dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
		dateFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
		timeFormatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);
	}

	public String format(LocalDateTime localDateTime) {
		if (localDateTime == null) return "";
		return dateTimeFormatter.format(localDateTime);
	}

	public String format(LocalDate localDate) {
		if (localDate == null) return "";
		return dateFormatter.format(localDate);
	}

	public String format(LocalTime localTime) {
		if (localTime == null) return "";
		return timeFormatter.format(localTime);
	}

	public String get(Boolean b) {
		return get(String.valueOf(b));
	}
}
