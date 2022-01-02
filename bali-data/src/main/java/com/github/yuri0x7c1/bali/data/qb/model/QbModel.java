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

package com.github.yuri0x7c1.bali.data.qb.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * Instantiates a new model.
 */
@FieldDefaults(level=AccessLevel.PRIVATE)
@Data
public class QbModel {

	@ToString
	@Data
	@FieldDefaults(level=AccessLevel.PRIVATE)
	public static class QbGroup {
		QbCondition condition;

		List<QbGroup> groups = new ArrayList<>();

		List<QbRule> rules = new ArrayList<>();
	}

	@ToString
	@Data
	@FieldDefaults(level=AccessLevel.PRIVATE, makeFinal = true)
	public static class QbRule {
		String fieldName;

		QbType fieldType;

		QbOperator operator;

		Object value;
	}

	QbGroup rootGroup;

	/**
	 * Group is empty.
	 *
	 * @param group the group
	 * @return true, if successful
	 */
	private boolean groupIsEmpty(QbGroup group) {
		if (CollectionUtils.isNotEmpty(group.getRules())) return false;

		if (CollectionUtils.isNotEmpty(group.getGroups())) {
			for (QbGroup g : group.getGroups()) {
				if (!groupIsEmpty(g)) return false;
			}
		}

		return true;
	}

	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty () {
		return groupIsEmpty(rootGroup);
	}

	/**
	 * Checks if is not empty.
	 *
	 * @return true, if is not empty
	 */
	public boolean isNotEmpty() {
		return !groupIsEmpty(rootGroup);
	}
}
