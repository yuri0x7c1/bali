package com.github.yuri0x7c1.bali.data.entity;

import java.util.function.Function;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityProperty<T> {
	@Getter
	final String name;

	@Getter
	final String caption;

	@Getter
	boolean sortable = true;

	@Getter
	Function<T, String> valueProvider;

	public EntityProperty(String name, String caption) {
		this.name = name;
		this.caption = caption;
	}

	public EntityProperty(String name, String caption, boolean sortable) {
		this.name = name;
		this.caption = caption;
		this.sortable = sortable;
	}

	public EntityProperty(String name, String caption, Function<T, String> valueProvider) {
		this.name = name;
		this.caption = caption;
		this.valueProvider = valueProvider;
	}

	public EntityProperty(String name, String caption, boolean sortable, Function<T, String> valueProvider) {
		this.name = name;
		this.caption = caption;
		this.sortable = sortable;
		this.valueProvider = valueProvider;
	}
}
