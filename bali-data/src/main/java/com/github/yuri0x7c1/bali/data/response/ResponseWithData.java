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

package com.github.yuri0x7c1.bali.data.response;

import org.springframework.data.domain.Sort.Direction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ResponseWithData<T> extends Response {

	protected T data;
	protected Long dataCount;
	protected String[] orderBy;
	protected String order;

	public ResponseWithData(Exception ex) {
		super(ex);
	}

	@JsonIgnore
	public Direction getDefaultOrder() {
		return Direction.ASC;
	}

	@JsonIgnore
	public String[] getDefaultOrderBy() {
		return new String[] {"id"};
	}
}
