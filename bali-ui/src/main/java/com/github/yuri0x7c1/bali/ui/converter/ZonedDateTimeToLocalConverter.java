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
public class ZonedDateTimeToLocalConverter implements Converter<ZonedDateTime, LocalDateTime>{

	@Override
	public Result<LocalDateTime> convertToModel(ZonedDateTime value, ValueContext context) {
		if (value == null) {
			return Result.ok(null);
		}
		return Result.ok(value.toLocalDateTime());
	}

	@Override
	public ZonedDateTime convertToPresentation(LocalDateTime value, ValueContext context) {
		if (value == null) {
			return null;
		}
		return ZonedDateTime.of(value, ZoneId.systemDefault());
	}
}
