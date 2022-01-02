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

package com.github.yuri0x7c1.bali.demo.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import com.github.yuri0x7c1.bali.demo.domain.Bar;

/**
 * Foo Base Repository
 */
@Repository
public interface FooBaseRepository extends JpaRepository<Foo, Integer>, JpaSpecificationExecutor<Foo> {
	/* Finder methods for entity fields */
	/* stringValue */
	Page<Foo> findByStringValue(String stringValue, Pageable pageable);

	long countByStringValue(String stringValue);

	Page<Foo> findByStringValueLike(String stringValue, Pageable pageable);

	long countByStringValueLike(String stringValue);

	Page<Foo> findByStringValueLikeIgnoreCase(String stringValue, Pageable pageable);

	long countByStringValueLikeIgnoreCase(String stringValue);

	Page<Foo> findByStringValueContaining(String stringValue, Pageable pageable);

	long countByStringValueContaining(String stringValue);

	Page<Foo> findByStringValueContainingIgnoreCase(String stringValue, Pageable pageable);

	long countByStringValueContainingIgnoreCase(String stringValue);

	/* longValue */
	Page<Foo> findByLongValue(Long longValue, Pageable pageable);

	long countByLongValue(Long longValue);

	/* doubleValue */
	Page<Foo> findByDoubleValue(Double doubleValue, Pageable pageable);

	long countByDoubleValue(Double doubleValue);

	/* booleanValue */
	Page<Foo> findByBooleanValue(Boolean booleanValue, Pageable pageable);

	long countByBooleanValue(Boolean booleanValue);

	/* date */
	Page<Foo> findByDate(Date date, Pageable pageable);

	long countByDate(Date date);

	/* instant */
	Page<Foo> findByInstant(Instant instant, Pageable pageable);

	long countByInstant(Instant instant);

	/* localDateTime */
	Page<Foo> findByLocalDateTime(LocalDateTime localDateTime, Pageable pageable);

	long countByLocalDateTime(LocalDateTime localDateTime);

	/* zonedDateTime */
	Page<Foo> findByZonedDateTime(ZonedDateTime zonedDateTime, Pageable pageable);

	long countByZonedDateTime(ZonedDateTime zonedDateTime);

	/* localDate */
	Page<Foo> findByLocalDate(LocalDate localDate, Pageable pageable);

	long countByLocalDate(LocalDate localDate);

	/* bar */
	Page<Foo> findByBar(Bar bar, Pageable pageable);

	long countByBar(Bar bar);

}
