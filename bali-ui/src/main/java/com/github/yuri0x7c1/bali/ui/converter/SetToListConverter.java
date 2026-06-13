package com.github.yuri0x7c1.bali.ui.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

public class SetToListConverter<T> implements Converter<Set<T>, List<T>> {

	@Override
	public Result<List<T>> convertToModel(Set<T> value, ValueContext context) {
		if (CollectionUtils.isEmpty(value)) {
			return Result.ok(Collections.emptyList());
		}
		return Result.ok(Collections.unmodifiableList(new ArrayList<>(value)));
	}

	@Override
	public Set<T> convertToPresentation(List<T> value, ValueContext context) {
		if (CollectionUtils.isEmpty(value)) {
			return Collections.emptySet();
		}
		return Collections.unmodifiableSet(new LinkedHashSet<>(value));
	}
}
