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

package com.github.yuri0x7c1.bali.ui.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

/**
 *
 * @author yuri0x7c1
 *
 */
public class LocalDateTimeToZonedConverter implements Converter<LocalDateTime, ZonedDateTime>{

	@Override
	public Result<ZonedDateTime> convertToModel(LocalDateTime value, ValueContext context) {
		if (value == null) {
			return Result.ok(null);
		}
		return Result.ok(ZonedDateTime.of(value, ZoneId.systemDefault()));
	}

	@Override
	public LocalDateTime convertToPresentation(ZonedDateTime value, ValueContext context) {
		if (value == null) {
			return null;
		}
		return value.toLocalDateTime();
	}
}
