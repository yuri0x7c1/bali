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
