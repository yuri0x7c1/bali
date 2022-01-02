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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 * @param <T>
 */
@Slf4j
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
}
