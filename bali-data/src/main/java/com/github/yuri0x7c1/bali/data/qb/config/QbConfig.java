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

package com.github.yuri0x7c1.bali.data.qb.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.yuri0x7c1.bali.data.qb.model.QbField;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QbConfig {
	List<QbField> fields = new ArrayList<>();

	public QbConfig(List<QbField> fields) {
		this.fields.addAll(fields);
	}

	public List<QbField> getFields() {
		return Collections.unmodifiableList(fields);
	}

	/**
	 * Builder to build {@link QbConfig}.
	 */
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static final class Builder {
		List<QbField> fields = new ArrayList<>();

		public Builder withFields(QbField... fields) {
			this.fields = Arrays.asList(fields);
			return this;
		}

		public Builder addField(QbField field) {
			this.fields.add(field);
			return this;
		}

		public QbConfig build() {
			return new QbConfig(fields);
		}
	}


}
