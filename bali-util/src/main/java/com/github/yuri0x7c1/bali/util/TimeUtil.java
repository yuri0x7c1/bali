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

	public static List<LocalDateTime> getLocalDateTimePeriod(TimePeriod timePeriod) {
		if (TimePeriod.TODAY.equals(timePeriod)) {
			LocalDateTime dt = LocalDateTime.now();
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfDay(dt), getEndOfDay(dt) }));
		}
		if (TimePeriod.YESTERDAY.equals(timePeriod)) {
			LocalDateTime dt = LocalDateTime.now().minusDays(1);
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfDay(dt), getEndOfDay(dt) }));
		}
		else if (TimePeriod.CURRENT_WEEK.equals(timePeriod)) {
			LocalDateTime dt = LocalDateTime.now();
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfWeek(dt), getEndOfWeek(dt) }));
		}
		else if (TimePeriod.PREVIOUS_WEEK.equals(timePeriod)) {
			LocalDateTime dt = LocalDateTime.now().minusWeeks(1);
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfWeek(dt), getEndOfWeek(dt) }));
		}
		else if (TimePeriod.CURRENT_MONTH.equals(timePeriod)) {
			LocalDateTime dt = LocalDateTime.now();
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfMonth(dt), getEndOfMonth(dt) }));
		}
		else if (TimePeriod.PREVIOUS_MOHTH.equals(timePeriod)) {
			LocalDateTime dt = LocalDateTime.now().minusMonths(1);
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfMonth(dt), getEndOfMonth(dt) }));
		}
		else if (TimePeriod.CURRENT_YEAR.equals(timePeriod)) {
			LocalDateTime dt = LocalDateTime.now();
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfYear(dt), getEndOfYear(dt) }));
		}
		else if (TimePeriod.PREVIOUS_YEAR.equals(timePeriod)) {
			LocalDateTime dt = LocalDateTime.now().minusYears(1);
			return Collections
					.unmodifiableList(Arrays.asList(new LocalDateTime[] { getStartOfYear(dt), getEndOfYear(dt) }));
		}
		throw new RuntimeException("Unsupported TimePeriod!");
	}

	public static List<ZonedDateTime> getZonedDateTimePeriod(TimePeriod timePeriod) {
		if (TimePeriod.TODAY.equals(timePeriod)) {
			ZonedDateTime dt = ZonedDateTime.now();
			return Collections
					.unmodifiableList(Arrays.asList(new ZonedDateTime[] { getStartOfDay(dt), getEndOfDay(dt) }));
		}
		if (TimePeriod.YESTERDAY.equals(timePeriod)) {
			ZonedDateTime dt = ZonedDateTime.now().minusDays(1);
			return Collections
					.unmodifiableList(Arrays.asList(new ZonedDateTime[] { getStartOfDay(dt), getEndOfDay(dt) }));
		}
		else if (TimePeriod.CURRENT_WEEK.equals(timePeriod)) {
			ZonedDateTime dt = ZonedDateTime.now();
			return Collections
					.unmodifiableList(Arrays.asList(new ZonedDateTime[] { getStartOfWeek(dt), getEndOfWeek(dt) }));
		}
		else if (TimePeriod.PREVIOUS_WEEK.equals(timePeriod)) {
			ZonedDateTime dt = ZonedDateTime.now().minusWeeks(1);
			return Collections
					.unmodifiableList(Arrays.asList(new ZonedDateTime[] { getStartOfWeek(dt), getEndOfWeek(dt) }));
		}
		else if (TimePeriod.CURRENT_MONTH.equals(timePeriod)) {
			ZonedDateTime dt = ZonedDateTime.now();
			return Collections
					.unmodifiableList(Arrays.asList(new ZonedDateTime[] { getStartOfMonth(dt), getEndOfMonth(dt) }));
		}
		else if (TimePeriod.PREVIOUS_MOHTH.equals(timePeriod)) {
			ZonedDateTime dt = ZonedDateTime.now().minusMonths(1);
			return Collections
					.unmodifiableList(Arrays.asList(new ZonedDateTime[] { getStartOfMonth(dt), getEndOfMonth(dt) }));
		}
		else if (TimePeriod.CURRENT_YEAR.equals(timePeriod)) {
			ZonedDateTime dt = ZonedDateTime.now();
			return Collections
					.unmodifiableList(Arrays.asList(new ZonedDateTime[] { getStartOfYear(dt), getEndOfYear(dt) }));
		}
		else if (TimePeriod.PREVIOUS_YEAR.equals(timePeriod)) {
			ZonedDateTime dt = ZonedDateTime.now().minusYears(1);
			return Collections
					.unmodifiableList(Arrays.asList(new ZonedDateTime[] { getStartOfYear(dt), getEndOfYear(dt) }));
		}
		throw new RuntimeException("Unsupported TimePeriod!");
	}
}
