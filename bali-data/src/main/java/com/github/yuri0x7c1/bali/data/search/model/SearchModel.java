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

package com.github.yuri0x7c1.bali.data.search.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author yuri0x7c1
 *
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchModel {
	public enum LogicalOperator {
		AND,
		OR
	}

	List<SearchField> fields = new ArrayList<>();
	LogicalOperator logicalOperator = LogicalOperator.AND;

	public void removeFieldsByName(String name) {
		List<SearchField> newFields = new ArrayList<>();
		for (SearchField f : fields) {
			if (!name.equals(f.getName())) {
				newFields.add(f);
			}
		}
		fields = newFields;
	}
}
