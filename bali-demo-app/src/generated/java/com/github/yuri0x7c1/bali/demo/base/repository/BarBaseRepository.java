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

import com.github.yuri0x7c1.bali.demo.domain.Bar;

/**
 * Bar Base Repository
 */
@Repository
public interface BarBaseRepository extends JpaRepository<Bar, Integer>, JpaSpecificationExecutor<Bar> {
	/* Finder methods for entity fields */
	/* value */
	Page<Bar> findByValue(String value, Pageable pageable);

	long countByValue(String value);

	Page<Bar> findByValueLike(String value, Pageable pageable);

	long countByValueLike(String value);

	Page<Bar> findByValueLikeIgnoreCase(String value, Pageable pageable);

	long countByValueLikeIgnoreCase(String value);

	Page<Bar> findByValueContaining(String value, Pageable pageable);

	long countByValueContaining(String value);

	Page<Bar> findByValueContainingIgnoreCase(String value, Pageable pageable);

	long countByValueContainingIgnoreCase(String value);

}
