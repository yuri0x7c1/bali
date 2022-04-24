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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.viritin.fields.DoubleField;
import org.vaadin.viritin.fields.IntegerField;

import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.github.yuri0x7c1.bali.ui.field.BigDecimalField;
import com.github.yuri0x7c1.bali.ui.field.BigIntegerField;
import com.github.yuri0x7c1.bali.ui.field.BooleanField;
import com.github.yuri0x7c1.bali.ui.field.DateTimeRangeField;
import com.github.yuri0x7c1.bali.ui.field.LongField;
import com.github.yuri0x7c1.bali.util.TextUtil;
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

	private final String fieldCaption;

	private final Class<?> fieldType;

	// private final Class<? extends Component> componentClass;

	// private final SearchFieldComponentLifecycle componentLifecycle;

	private final Map<SearchFieldOperator, SearchFielComponentDescription> componentDescriptions = new LinkedHashMap<>();

	private SearchFieldComponentDescriptor(Builder builder) {
		this.fieldName = TextUtil.requireNonBlank(builder.fieldName);
		this.fieldType = Objects.requireNonNull(builder.fieldType);

		if (StringUtils.isBlank(builder.fieldCaption)) {
			this.fieldCaption = TextUtil.createCaptionFromCamelCase(builder.fieldName);
		}
		else {
			this.fieldCaption = builder.fieldCaption;
		}

		componentDescriptions.putAll(getDefaultComponentDescriptions());
		componentDescriptions.putAll(builder.componentDescriptions);

		log.debug("!!! componentDescriptions : {}", componentDescriptions);
	}

	@Deprecated
	public SearchFieldComponentDescriptor(String fieldName, String fieldCaption, Class<?> fieldType,
			Class<? extends Component> componentClass, SearchFieldComponentLifecycle componentLifecycle) {
		this(new Builder().withFieldName(fieldName).withFieldCaption(fieldCaption).withFieldType(fieldType)
				.withComponent(componentClass, componentLifecycle, SearchFieldOperator.values()));
	}

	private Map<SearchFieldOperator, SearchFielComponentDescription> getDefaultComponentDescriptions() {
		Map<SearchFieldOperator, SearchFielComponentDescription> cds = new LinkedHashMap<>();
		for (SearchFieldOperator operator : SearchFieldOperator.values()) {
			if (fieldType.equals(String.class)) {
				if (EQUAL.equals(operator) || NOT_EQUAL.equals(operator) || CONTAINS.equals(operator)) {
					cds.put(operator, new SearchFielComponentDescription(IntegerField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Integer.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFielComponentDescription(IntegerField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Long.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFielComponentDescription(LongField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Double.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFielComponentDescription(DoubleField.class));
				}
			}
			else if (fieldType.equals(BigInteger.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFielComponentDescription(BigIntegerField.class));
				}
			}
			else if (fieldType.equals(BigDecimal.class)) {
				if (!CONTAINS.equals(operator) && !INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFielComponentDescription(BigDecimalField.class));
				}
			}
			else if (fieldType.isAssignableFrom(Boolean.class)) {
				if (EQUAL.equals(operator) || NOT_EQUAL.equals(operator)) {
					cds.put(operator, new SearchFielComponentDescription(BooleanField.class, MANAGED));
				}
			}
			else if (fieldType.equals(LocalDateTime.class)) {
				if (INTERVAL.equals(operator)) {
					cds.put(operator, new SearchFielComponentDescription(DateTimeRangeField.class, MANAGED));
				}
				else if (CONTAINS.equals(operator)) {
				}
				else {
					cds.put(operator, new SearchFielComponentDescription(DateTimeField.class));
				}
			}
		}
		return cds;
	}

	public SearchFielComponentDescription getComponentDescription(SearchFieldOperator operator) {
		return componentDescriptions.get(operator);
	}

	public Set<SearchFieldOperator> getValidOperators() {
		return componentDescriptions.keySet();
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
		private final Map<SearchFieldOperator, SearchFielComponentDescription> componentDescriptions = new HashMap<>();

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

		public Builder withComponent(Class<? extends Component> componentClass,
				SearchFieldComponentLifecycle componentLifecycle, SearchFieldOperator... operators) {
			for (SearchFieldOperator operator : operators) {
				this.componentDescriptions.put(operator, new SearchFielComponentDescription(componentClass, componentLifecycle));
			}
			return this;
		}

		public Builder withComponentExcludeOperators(Class<? extends Component> componentClass,
				SearchFieldComponentLifecycle componentLifecycle, SearchFieldOperator... excludeOperators) {
			for (SearchFieldOperator operator : ArrayUtils.removeElements(SearchFieldOperator.values(), excludeOperators)) {
				this.componentDescriptions.put(operator, new SearchFielComponentDescription(componentClass, componentLifecycle));
			}
			return this;
		}

		public SearchFieldComponentDescriptor build() {
			return new SearchFieldComponentDescriptor(this);
		}
	}
}
