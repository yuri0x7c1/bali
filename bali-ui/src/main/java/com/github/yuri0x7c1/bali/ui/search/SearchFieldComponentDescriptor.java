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

import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.fields.DoubleField;
import org.vaadin.viritin.fields.IntegerField;
import org.vaadin.viritin.fields.MTextField;

import com.github.yuri0x7c1.bali.ui.field.BigDecimalField;
import com.github.yuri0x7c1.bali.ui.field.BigIntegerField;
import com.github.yuri0x7c1.bali.ui.field.BooleanField;
import com.github.yuri0x7c1.bali.ui.field.LongField;
import com.vaadin.ui.Component;

import lombok.Data;


/**
 * The Class SearchFieldComponentDescriptor.
 */
@Data
public class SearchFieldComponentDescriptor implements Comparable<SearchFieldComponentDescriptor> {
	private final String fieldName;

	private final String fieldCaption;

	private final Class<?> fieldType;

	private final Class<? extends Component> componentClass;

	private final SearchFieldComponentLifecycle componentLifecycle;

	public SearchFieldComponentDescriptor(String fieldName, String fieldCaption, Class<?> fieldType,
			Class<? extends Component> componentClass, SearchFieldComponentLifecycle componentLifecycle) {

		this.fieldName = fieldName;
		this.fieldType = fieldType;

		this.fieldCaption = fieldCaption;

		if (componentClass == null) {
			this.componentClass = getDefaultComponentClass();
		}
		else {
			this.componentClass = componentClass;
		}

		if (componentLifecycle == null) {
			this.componentLifecycle = getDefaultComponentLifecycle();
		}
		else {
			this.componentLifecycle = componentLifecycle;
		}
	}

	private Class<? extends Component> getDefaultComponentClass() {
		// TODO: use isAssignableFrom
		if (Integer.class.equals(fieldType)) {
			return IntegerField.class;
		}
		if (Long.class.equals(fieldType)) {
			return LongField.class;
		}
		if (Double.class.equals(fieldType)) {
			return DoubleField.class;
		}
		if (BigInteger.class.equals(fieldType)) {
			return BigIntegerField.class;
		}
		if (BigDecimal.class.equals(fieldType)) {
			return BigDecimalField.class;
		}
		if (Boolean.class.equals(fieldType)) {
			return BooleanField.class;
		}
		return MTextField.class;
	}

	private SearchFieldComponentLifecycle getDefaultComponentLifecycle() {
		if (fieldType.isAssignableFrom(componentClass)) {
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
			this.fieldCaption = fieldCaption;
			return this;
		}

		public Builder withFieldType(Class<?> fieldType) {
			this.fieldType = fieldType;
			return this;
		}

		public Builder withComponentClass(Class<? extends Component> componentClass) {
			this.componentClass = componentClass;
			return this;
		}

		public Builder withComponentLifecycle(SearchFieldComponentLifecycle componentLifecycle) {
			this.componentLifecycle = componentLifecycle;
			return this;
		}

		public SearchFieldComponentDescriptor build() {
			return new SearchFieldComponentDescriptor(fieldName, fieldCaption, fieldType, componentClass,
					componentLifecycle);
		}
	}
}
