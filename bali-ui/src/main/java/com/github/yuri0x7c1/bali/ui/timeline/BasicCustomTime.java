package com.github.yuri0x7c1.bali.ui.timeline;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class BasicCustomTime implements CustomTime {
	@Getter
	private final Date time;

	@Getter
	private final String id;

	@Getter
	private String title;

	@Getter
	private boolean isMarker = false;

	@Getter
	private boolean isEditable = false;
}
