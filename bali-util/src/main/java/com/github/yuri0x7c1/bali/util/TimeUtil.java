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

package com.github.yuri0x7c1.bali.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author yuri0x7c1
 *
 */
public class TimeUtil {
	public static LocalDateTime getStartOfHour(LocalDateTime dt) {
		return dt.truncatedTo(ChronoUnit.HOURS);
	}

	public static LocalDateTime getEndOfHour(LocalDateTime dt) {
		return getStartOfHour(dt).plusHours(1).minusSeconds(1);
	}

	public static LocalDateTime getStartOfDay(LocalDateTime dt) {
		return dt.truncatedTo(ChronoUnit.DAYS);
	}

	public static LocalDateTime getEndOfDay(LocalDateTime dt) {
		return getStartOfDay(dt).plusDays(1).minusSeconds(1);
	}

	public static LocalDateTime getStartOfWeek(LocalDateTime dt) {
		return dt.with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
	}

	public static LocalDateTime getEndOfWeek(LocalDateTime dt) {
		return getStartOfWeek(dt).plusDays(7).minusSeconds(1);
	}

	public static LocalDateTime getStartOfMonth(LocalDateTime dt) {
		return dt.with(TemporalAdjusters.firstDayOfMonth())
			.truncatedTo(ChronoUnit.DAYS);
	}

	public static LocalDateTime getEndOfMonth(LocalDateTime dt) {
		return dt.with(TemporalAdjusters.lastDayOfMonth())
			.truncatedTo(ChronoUnit.DAYS)
			.plusDays(1)
			.minusSeconds(1);
	}

	public static LocalDateTime getStartOfYear(LocalDateTime dt) {
		return dt.with(TemporalAdjusters.firstDayOfYear())
			.truncatedTo(ChronoUnit.DAYS);
	}

	public static LocalDateTime getEndOfYear(LocalDateTime dt) {
		return dt.with(TemporalAdjusters.lastDayOfYear())
			.truncatedTo(ChronoUnit.DAYS)
			.plusDays(1)
			.minusSeconds(1);
	}

	public static ZonedDateTime getStartOfHour(ZonedDateTime dt) {
		return dt.truncatedTo(ChronoUnit.HOURS);
	}

	public static ZonedDateTime getEndOfHour(ZonedDateTime dt) {
		return getStartOfHour(dt).plusHours(1).minusSeconds(1);
	}

	public static ZonedDateTime getStartOfDay(ZonedDateTime dt) {
		return dt.truncatedTo(ChronoUnit.DAYS);
	}

	public static ZonedDateTime getEndOfDay(ZonedDateTime dt) {
		return getStartOfDay(dt).plusDays(1).minusSeconds(1);
	}

	public static ZonedDateTime getStartOfWeek(ZonedDateTime dt) {
		return dt.with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
	}

	public static ZonedDateTime getEndOfWeek(ZonedDateTime dt) {
		return getStartOfWeek(dt).plusDays(7).minusSeconds(1);
	}

	public static ZonedDateTime getStartOfMonth(ZonedDateTime dt) {
		return dt.with(TemporalAdjusters.firstDayOfMonth())
			.truncatedTo(ChronoUnit.DAYS);
	}

	public static ZonedDateTime getEndOfMonth(ZonedDateTime dt) {
		return dt.with(TemporalAdjusters.lastDayOfMonth())
			.truncatedTo(ChronoUnit.DAYS)
			.plusDays(1)
			.minusSeconds(1);
	}

	public static ZonedDateTime getStartOfYear(ZonedDateTime dt) {
		return dt.with(TemporalAdjusters.firstDayOfYear())
			.truncatedTo(ChronoUnit.DAYS);
	}

	public static ZonedDateTime getEndOfYear(ZonedDateTime dt) {
		return dt.with(TemporalAdjusters.lastDayOfYear())
			.truncatedTo(ChronoUnit.DAYS)
			.plusDays(1)
			.minusSeconds(1);
	}

	public static List<LocalDateTime> getTimePeriod(TimePeriod timePeriod) {
		LocalDateTime now = LocalDateTime.now();
		if (TimePeriod.TODAY.equals(timePeriod)) {
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfDay(now), getEndOfDay(now) }));
		}
		throw new RuntimeException("Unsupported TimePeriod!");
	}
}
