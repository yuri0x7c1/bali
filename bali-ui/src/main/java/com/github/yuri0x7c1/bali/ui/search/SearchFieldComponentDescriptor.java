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

package com.github.yuri0x7c1.bali.ui.search;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.fields.DoubleField;
import org.vaadin.viritin.fields.IntegerField;
import org.vaadin.viritin.fields.MTextField;

import com.github.yuri0x7c1.bali.ui.field.BigDecimalField;
import com.github.yuri0x7c1.bali.ui.field.BigIntegerField;
import com.github.yuri0x7c1.bali.ui.field.BooleanField;
import com.github.yuri0x7c1.bali.ui.field.LongField;
import com.github.yuri0x7c1.bali.util.json.TextUtil;
import com.vaadin.ui.Component;

import lombok.Data;


/**
 * The Class SearchFieldComponentDescriptor.
 * @author yuri0x7c1
 */
@Data
public class SearchFieldComponentDescriptor implements Comparable<SearchFieldComponentDescriptor> {
	private final String fieldName;

	private final String fieldCaption;

	private final Class<?> fieldType;

	private final Class<? extends Component> componentClass;

	private final SearchFieldComponentLifecycle componentLifecycle;

	private SearchFieldComponentDescriptor(Builder builder) {
		this.fieldName = builder.fieldName;
		this.fieldType = builder.fieldType;

		if (StringUtils.isBlank(builder.fieldCaption)) {
			this.fieldCaption = TextUtil.createCaptionFromCamelCase(builder.fieldName);
		}
		else {
			this.fieldCaption = builder.fieldCaption;
		}

		if (builder.componentClass == null) {
			this.componentClass = getDefaultComponentClass();
		}
		else {
			this.componentClass = builder.componentClass;
		}

		if (builder.componentLifecycle == null) {
			this.componentLifecycle = getDefaultComponentLifecycle();
		}
		else {
			this.componentLifecycle = builder.componentLifecycle;
		}
	}

	@Deprecated
	public SearchFieldComponentDescriptor(String fieldName, String fieldCaption, Class<?> fieldType,
			Class<? extends Component> componentClass, SearchFieldComponentLifecycle componentLifecycle) {
		this(new Builder().withFieldName(fieldName).withFieldCaption(fieldCaption).withFieldType(fieldType)
				.withComponentClass(componentClass).withComponentLifecycle(componentLifecycle));
	}

	private Class<? extends Component> getDefaultComponentClass() {
		if (fieldType.isAssignableFrom(Integer.class)) {
			return IntegerField.class;
		}
		if (fieldType.isAssignableFrom(Long.class)) {
			return LongField.class;
		}
		if (fieldType.isAssignableFrom(Double.class)) {
			return DoubleField.class;
		}
		if (fieldType.isAssignableFrom(BigInteger.class)) {
			return BigIntegerField.class;
		}
		if (fieldType.isAssignableFrom(BigDecimal.class)) {
			return BigDecimalField.class;
		}
		if (fieldType.isAssignableFrom(Boolean.class)) {
			return BooleanField.class;
		}
		return MTextField.class;
	}

	private SearchFieldComponentLifecycle getDefaultComponentLifecycle() {
		if (fieldType.isAssignableFrom(Boolean.class)) {
			return  SearchFieldComponentLifecycle.MANAGED;
		}
		else {
			return SearchFieldComponentLifecycle.NON_MANAGED;
		}
	}

	@Override
	public int compareTo(SearchFieldComponentDescriptor o) {
		return StringUtils.compare(o.getFieldCaption(), o.getFieldCaption());
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String fieldName;
		private String fieldCaption;
		private Class<?> fieldType;
		private Class<? extends Component> componentClass;
		private SearchFieldComponentLifecycle componentLifecycle;

		private Builder() {
		}

		public Builder withFieldName(String fieldName) {
			this.fieldName = fieldName;
			return this;
		}

		public Builder withFieldCaption(String fieldCaption) {
			Objects.requireNonNull(fieldCaption);
			this.fieldCaption = fieldCaption;
			return this;
		}

		public Builder withFieldType(Class<?> fieldType) {
			this.fieldType = fieldType;
			return this;
		}

		public Builder withComponentClass(Class<? extends Component> componentClass) {
			Objects.requireNonNull(componentClass);
			this.componentClass = componentClass;
			return this;
		}

		public Builder withComponentLifecycle(SearchFieldComponentLifecycle componentLifecycle) {
			Objects.requireNonNull(componentLifecycle);
			this.componentLifecycle = componentLifecycle;
			return this;
		}

		public SearchFieldComponentDescriptor build() {
			Objects.requireNonNull(fieldName);
			Objects.requireNonNull(fieldType);
			return new SearchFieldComponentDescriptor(this);
		}
	}
}
