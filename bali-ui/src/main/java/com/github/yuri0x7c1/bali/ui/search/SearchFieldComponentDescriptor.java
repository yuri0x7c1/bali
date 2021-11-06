package com.github.yuri0x7c1.bali.ui.search;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.ui.Component;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@Data
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
public class SearchFieldComponentDescriptor implements Comparable<SearchFieldComponentDescriptor> {
	String fieldName;

	Class<?> fieldType;

	Class<? extends Component> componentClass;

	SearchFieldComponentLifecycle componentLifecycle;

	String componentLabel;

	@Override
	public int compareTo(SearchFieldComponentDescriptor o) {
		return StringUtils.compare(o.getComponentLabel(), o.getComponentLabel());
	}
}
