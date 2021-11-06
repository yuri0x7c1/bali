package com.github.yuri0x7c1.bali.data.qb.model;

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Value
public class QbField {
	@NonNull
	String name;

	@NonNull
	QbType type;

	String caption;

	@Override
	public String toString() {
		if (StringUtils.isNotBlank(caption)) return caption;
		return name;
	}
}
