package com.github.yuri0x7c1.bali.data.qb.model;

import static com.github.yuri0x7c1.bali.data.qb.model.QbOperator.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.ArrayUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum QbType {
	@JsonProperty("string")
	STRING(new QbOperator[] { EQUAL, NOT_EQUAL, CONTAINS, NOT_CONTAINS, BEGINS_WITH, NOT_BEGINS_WITH, ENDS_WITH,
			NOT_ENDS_WITH
	}, new Class<?>[] { String.class }),

	@JsonProperty("integer")
	INTEGER(new QbOperator[] { EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL },
			new Class<?>[] { Integer.class }),

	@JsonProperty("long")
	LONG(new QbOperator[] { EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL },
			new Class<?>[] { Long.class }),

	@JsonProperty("double")
	DOUBLE(new QbOperator[] { EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL },
			new Class<?>[] { Double.class }),

	@JsonProperty("date")
	DATE(new QbOperator[] { EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL },
			new Class<?>[] { LocalDate.class, java.util.Date.class }),

	@JsonProperty("time")
	TIME(new QbOperator[] { EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL },
			new Class<?>[] { LocalTime.class, java.util.Date.class }),

	@JsonProperty("datetime")
	DATETIME(new QbOperator[] { EQUAL, NOT_EQUAL, GREATER, GREATER_OR_EQUAL, LESS, LESS_OR_EQUAL },
			new Class<?>[] { LocalDateTime.class, Instant.class, ZonedDateTime.class, java.util.Date.class }),

	@JsonProperty("boolean")
	BOOLEAN(new QbOperator[] { EQUAL, NOT_EQUAL }, new Class<?>[] { Boolean.class });

	@Getter
	@NonNull
	private QbOperator[] operators;

	@Getter
	@NonNull
	private Class<?>[] javaTypes;

	public static QbType getByJavaType(Class<?> javaType) {
		for (QbType value : values()) {
			if (ArrayUtils.contains(value.getJavaTypes(), javaType)) {
				return value;
			}
		}
		return null;
	}


}