package com.github.yuri0x7c1.bali.util;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class TimeUtil {
	public static ZonedDateTime getStartOfCurrentHour(ZoneId zoneId) {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId).truncatedTo(ChronoUnit.HOURS);
		return zonedDateTime;
	}

	public static ZonedDateTime getStartOfCurrentDay(ZoneId zoneId) {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId).truncatedTo(ChronoUnit.DAYS);
		return zonedDateTime;
	}

	public static ZonedDateTime getStartOfCurrentWeek(ZoneId zoneId) {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId).with(DayOfWeek.MONDAY).truncatedTo(ChronoUnit.DAYS);
		return zonedDateTime;
	}

	public static ZonedDateTime getStartOfMonth(ZonedDateTime dt, ZoneId zoneId) {
		ZonedDateTime zonedDateTime = dt.withZoneSameInstant(zoneId)
			.with(TemporalAdjusters.firstDayOfMonth())
			.truncatedTo(ChronoUnit.DAYS);
		return zonedDateTime;
	}

	public static ZonedDateTime getEndOfMonth(ZonedDateTime dt, ZoneId zoneId) {
		ZonedDateTime zonedDateTime = dt.withZoneSameInstant(zoneId)
			.with(TemporalAdjusters.lastDayOfMonth())
			.truncatedTo(ChronoUnit.DAYS)
			.plusDays(1)
			.minusSeconds(1);
		return zonedDateTime;
	}

	public static ZonedDateTime getStartOfCurrentMonth(ZoneId zoneId) {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId).with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS);
		return zonedDateTime;
	}

	public static ZonedDateTime getStartOfYear(ZonedDateTime dt, ZoneId zoneId) {
		ZonedDateTime zonedDateTime = dt.withZoneSameInstant(zoneId)
			.with(TemporalAdjusters.firstDayOfYear())
			.truncatedTo(ChronoUnit.DAYS);
		return zonedDateTime;
	}

	public static ZonedDateTime getEndOfYear(ZonedDateTime dt, ZoneId zoneId) {
		ZonedDateTime zonedDateTime = dt.withZoneSameInstant(zoneId)
			.with(TemporalAdjusters.lastDayOfYear())
			.truncatedTo(ChronoUnit.DAYS)
			.plusDays(1)
			.minusSeconds(1);
		return zonedDateTime;
	}

	public static ZonedDateTime getStartOfCurrentYear(ZoneId zoneId) {
		ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId).with(TemporalAdjusters.firstDayOfYear()).truncatedTo(ChronoUnit.DAYS);
		return zonedDateTime;
	}

	public static ZonedDateTime getStartOfDay(ZonedDateTime dt, ZoneId zoneId) {
		ZonedDateTime zonedDateTime = dt.withZoneSameInstant(zoneId).truncatedTo(ChronoUnit.DAYS);
		return zonedDateTime;
	}

	public static ZonedDateTime getEndOfDay(ZonedDateTime dt, ZoneId zoneId) {
		ZonedDateTime zonedDateTime = dt.withZoneSameInstant(zoneId)
			.truncatedTo(ChronoUnit.DAYS)
			.plusDays(1)
			.minusSeconds(1);
		return zonedDateTime;
	}

	public static LocalDateTime getStartOfCurrentDay() {
		return LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
	}

	public static LocalDateTime getEndOfCurrentDay() {
		return LocalDateTime.now().truncatedTo(ChronoUnit.DAYS).plusDays(1).minusSeconds(1);
	}
}
