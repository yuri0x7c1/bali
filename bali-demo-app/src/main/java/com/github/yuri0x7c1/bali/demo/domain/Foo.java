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

package com.github.yuri0x7c1.bali.demo.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(of = "id")
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
public class Foo {

	@Getter
	@GeneratedValue
	@Id
	Integer id;

	@Getter
	@Setter
	String stringValue;

	@Min(value=10L)
	@Getter
	@Setter
	Long longValue;

	@Getter
	@Setter
	Double doubleValue;

	@Getter
	@Setter
	Boolean booleanValue;

	@Getter
	@Setter
	Date date;


	@Getter
	@Setter
	Instant instant;

	@Getter
	@Setter
	LocalDateTime localDateTime;

	@Getter
	@Setter
	ZonedDateTime zonedDateTime;

	@Getter
	@Setter
	LocalDate localDate;

	@Getter
	@Setter
	@ManyToOne(cascade = CascadeType.ALL)
	Bar bar = new Bar();

	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.EAGER)
	List<Bar> linkedBars = new ArrayList<>();
}
