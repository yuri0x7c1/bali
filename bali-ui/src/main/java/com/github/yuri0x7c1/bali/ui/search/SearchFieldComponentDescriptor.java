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

import static com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator.*;
import static com.github.yuri0x7c1.bali.ui.search.SearchFieldComponentLifecycle.MANAGED;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.fields.DoubleField;
import org.vaadin.viritin.fields.IntegerField;
import org.vaadin.viritin.fields.MTextField;

import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.github.yuri0x7c1.bali.ui.field.BigDecimalField;
import com.github.yuri0x7c1.bali.ui.field.BigIntegerField;
import com.github.yuri0x7c1.bali.ui.field.BooleanField;
import com.github.yuri0x7c1.bali.ui.field.ByteField;
import com.github.yuri0x7c1.bali.ui.field.LocalDateField;
import com.github.yuri0x7c1.bali.ui.field.LocalDateRangeField;
import com.github.yuri0x7c1.bali.ui.field.LocalDateTimeRangeField;
import com.github.yuri0x7c1.bali.ui.field.LongField;
import com.github.yuri0x7c1.bali.ui.field.ShortField;
import com.github.yuri0x7c1.bali.ui.field.ZonedDateTimeRangeField;
import com.github.yuri0x7c1.bali.util.TextUtil;
import com.vaadin.data.Converter;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateTimeField;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * The Class SearchFieldComponentDescriptor
 *
 * @author yuri0x7c1
 */
@Slf4j
@Data
public class SearchFieldComponentDescriptor implements Comparable<SearchFieldComponentDescriptor> {

	private final String fieldName;

	private final String caption;

	private final Class<?> fieldType;

	private final Map<SearchFieldOperator, SearchFieldComponentDescription> componentDescriptions = new LinkedHashMap<>();

	private Converter converter;

	private SearchFieldComponentDescriptor(Builder builder) {
		this.fieldName = TextUtil.requireNonBlank(builder.fieldName);
		this.fieldType = Objects.requireNonNull(builder.fieldType);

		if (StringUtils.isBlank(builder.caption)) {
			this.caption = TextUtil.createCaptionFromCamelCase(builder.fieldName);
		}
		else {
			this.caption = builder.caption;
		}

		this.converter = builder.converter;

		componentDescriptions.putAll(getDefaultComponentDescriptions());
		componentDescriptions.putAll(builder.componentDescriptions);
	}

	@Deprecated
	public SearchFieldComponentDescriptor(String fieldName, String fieldCaption, Class<?> fieldType,
			Class<? extends Component> componentClass, SearchFieldComponentLifecycle componentLifecycle) {
		this(new Builder().withFieldName(fieldName).withFieldCaption(fieldCaption).withFieldType(fieldType)
				.withComponent(componentClass, componentLifecycle, SearchFieldOperator.values()));
	}

	private Map<SearchFieldOperator, SearchFieldComponentDescription> getDefaultComponentDescriptions() {
		Map<SearchFieldOperator, SearchFieldComponentDescription> cds = new LinkedHashMap<>();
		for (SearchFieldOperator operator : SearchFieldOperator.values()) {
			if (fieldType.equals(String.class)) {
				if (EQUAL.equals(operator) || NOT_EQUAL.equals(operator) || CONTAINS.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(MTextField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Short.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator) && !IN.equals(operator) && !SPEC.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(ShortField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Byte.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator) && !IN.equals(operator) && !SPEC.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(ByteField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Integer.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator) && !IN.equals(operator) && !SPEC.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(IntegerField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Long.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator) && !IN.equals(operator) && !SPEC.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(LongField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Double.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator) && !IN.equals(operator) && !SPEC.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(DoubleField.class));
				}
			}
			else if (fieldType.equals(BigInteger.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator) && !IN.equals(operator) && !SPEC.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(BigIntegerField.class));
				}
			}
			else if (fieldType.equals(BigDecimal.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator) && !IN.equals(operator) && !SPEC.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(BigDecimalField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Boolean.class)) {
				if (EQUAL.equals(operator) || NOT_EQUAL.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(BooleanField.class, MANAGED));
				}
			}
			else if (fieldType.equals(ZonedDateTime.class)) {
				if (INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(ZonedDateTimeRangeField.class, MANAGED));
				}
				else if (CONTAINS.equals(operator)) {
				}
				else if (IN.equals(operator)) {
				}
				else {
					cds.put(operator, new SearchFieldComponentDescription(DateTimeField.class));
				}
			}
			else if (fieldType.equals(LocalDateTime.class)) {
				if (INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(LocalDateTimeRangeField.class, MANAGED));
				}
				else if (CONTAINS.equals(operator)) {
				}
				else if (IN.equals(operator)) {
				}
				else {
					cds.put(operator, new SearchFieldComponentDescription(DateTimeField.class));
				}
			}
			else if (fieldType.equals(LocalDate.class)) {
				if (INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFieldComponentDescription(LocalDateRangeField.class, MANAGED));
				}
				else if (CONTAINS.equals(operator)) {
				}
				else if (IN.equals(operator)) {
				}
				else {
					cds.put(operator, new SearchFieldComponentDescription(LocalDateField.class));
				}
			}
		}
		return cds;
	}

	public SearchFieldComponentDescription getComponentDescription(SearchFieldOperator operator) {
		return componentDescriptions.get(operator);
	}

	public Set<SearchFieldOperator> getValidOperators() {
		return componentDescriptions.keySet();
	}

	@Override
	public int compareTo(SearchFieldComponentDescriptor o) {
		return StringUtils.compare(o.getCaption(), o.getCaption());
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String fieldName;
		private String caption;
		private Class<?> fieldType;
		private final Map<SearchFieldOperator, SearchFieldComponentDescription> componentDescriptions = new HashMap<>();
		private Converter converter;

		private Builder() {
		}

		public Builder withFieldName(String fieldName) {
			this.fieldName = fieldName;
			return this;
		}

		@Deprecated
		public Builder withFieldCaption(String fieldCaption) {
			this.caption = fieldCaption;
			return this;
		}

		public Builder withCaption(String caption) {
			this.caption = caption;
			return this;
		}

		public Builder withFieldType(Class<?> fieldType) {
			this.fieldType = fieldType;
			return this;
		}

		public Builder withComponent(Supplier<? extends Component> componentSupplier, SearchFieldOperator... operators) {
			for (SearchFieldOperator operator : operators) {
				this.componentDescriptions.put(operator, new SearchFieldComponentDescription(componentSupplier));
			}
			return this;
		}

		public Builder withComponent(Class<? extends Component> componentClass,
				SearchFieldComponentLifecycle componentLifecycle, SearchFieldOperator... operators) {
			for (SearchFieldOperator operator : operators) {
				this.componentDescriptions.put(operator, new SearchFieldComponentDescription(componentClass, componentLifecycle));
			}
			return this;
		}

		public Builder withComponentExcludeOperators(Class<? extends Component> componentClass,
				SearchFieldComponentLifecycle componentLifecycle, SearchFieldOperator... excludeOperators) {
			for (SearchFieldOperator operator : ArrayUtils.removeElements(SearchFieldOperator.values(), excludeOperators)) {
				this.componentDescriptions.put(operator, new SearchFieldComponentDescription(componentClass, componentLifecycle));
			}
			return this;
		}

		public Builder withConverter(Converter converter) {
			this.converter = converter;
			return this;
		}

		public SearchFieldComponentDescriptor build() {
			return new SearchFieldComponentDescriptor(this);
		}
	}
}
