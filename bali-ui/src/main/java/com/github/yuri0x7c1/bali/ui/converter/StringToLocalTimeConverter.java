package com.github.yuri0x7c1.bali.ui.converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public Result<LocalTime> convertToModel(String value, ValueContext context) {
        if (StringUtils.isBlank(value)) {
            return Result.ok(null);
        }
        try {
	        LocalTime parsedValue = LocalTime.parse(value.trim(), DateTimeFormatter.ISO_LOCAL_TIME);
	        return Result.ok(parsedValue);
        }
        catch (DateTimeParseException ex) {
        	log.error(ex.getMessage(), ex);
        	return Result.error(ex.getMessage());
        }
    }

    @Override
    public String convertToPresentation(LocalTime value, ValueContext context) {
        if (value == null) {
            return "";
        }

        return DateTimeFormatter.ISO_LOCAL_TIME.format(value);
    }
}
