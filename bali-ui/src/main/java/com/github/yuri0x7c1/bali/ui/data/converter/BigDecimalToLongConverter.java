package com.github.yuri0x7c1.bali.ui.data.converter;

import java.math.BigDecimal;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

/**
 * BigDecimal to Long converter
 *
 * @author yuri0x7c1
 */
public class BigDecimalToLongConverter implements Converter<BigDecimal, Long> {
	@Override
	public Result<Long> convertToModel(BigDecimal value, ValueContext context) {
		if (value == null) return Result.ok(null);
		return Result.ok(value.longValue());
	}

	@Override
	public BigDecimal convertToPresentation(Long value, ValueContext context) {
		if (value == null) return null;
		return BigDecimal.valueOf(value);
	}
}