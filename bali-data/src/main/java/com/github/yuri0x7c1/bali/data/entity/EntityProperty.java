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

package com.github.yuri0x7c1.bali.data.entity;

import java.util.function.Function;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 */
@Slf4j
public class EntityProperty<T> {
	@Getter
	private final String name;

	@Getter
	private final String caption;

	@Getter
	private final boolean sortable;

	@Getter
	private final Function<T, String> valueProvider;

	private EntityProperty(Builder<T> builder) {
		this.name = builder.name;
		this.caption = builder.caption;
		this.sortable = builder.sortable;
		this.valueProvider = builder.valueProvider;
	}

	@Deprecated
	public EntityProperty(String name, String caption) {
		this(name, caption, true, null);
	}

	@Deprecated
	public EntityProperty(String name, String caption, boolean sortable) {
		this(name, caption, sortable, null);
	}

	@Deprecated
	public EntityProperty(String name, String caption, Function<T, String> valueProvider) {
		this(name, caption, true, valueProvider);
	}

	@Deprecated
	public EntityProperty(String name, String caption, boolean sortable, Function<T, String> valueProvider) {
		this(new Builder<T>().withName(name).withCaption(caption).withSortable(sortable)
				.withValueProvider(valueProvider));
	}

	public Object getValue(T entity) {
		try {
			Object value = null;
			if (valueProvider == null) {
				value = PropertyUtils.getNestedProperty(entity, name);
			}
			else {
				value = valueProvider.apply(entity);
			}
			return value;
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return null;
	}

	public String getValueAsString(T entity, String nullRepresentation) {
		try {
			String value = null;
			if (valueProvider == null) {
				value = BeanUtils.getProperty(entity, name);
			}
			else {
				value = valueProvider.apply(entity);
			}
			return StringUtils.isNotBlank(value) ? value : nullRepresentation;
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return nullRepresentation;
	}

	public String getValueAsString(T entity) {
		return getValueAsString(entity, "");
	}

	public static <T> Builder<T> builder() {
		return new Builder<T>();
	}

	public static final class Builder<T> {
		private String name;
		private String caption;
		private boolean sortable = true;
		private Function<T, String> valueProvider;

		private Builder() {
		}

		public Builder<T> withName(String name) {
			this.name = name;
			return this;
		}

		public Builder<T> withCaption(String caption) {
			this.caption = caption;
			return this;
		}

		public Builder<T> withSortable(boolean sortable) {
			this.sortable = sortable;
			return this;
		}

		public Builder<T> withValueProvider(Function<T, String> valueProvider) {
			this.valueProvider = valueProvider;
			return this;
		}

		public EntityProperty<T> build() {
			return new EntityProperty<T>(this);
		}
	}
}
