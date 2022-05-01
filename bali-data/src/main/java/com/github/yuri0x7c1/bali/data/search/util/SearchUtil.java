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

package com.github.yuri0x7c1.bali.data.search.util;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.jpa.domain.Specification;

import com.github.yuri0x7c1.bali.data.search.model.SearchField;
import com.github.yuri0x7c1.bali.data.search.model.SearchFieldOperator;
import com.github.yuri0x7c1.bali.data.search.model.SearchModel;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yuri0x7c1
 *
 */
@Slf4j
@SuppressWarnings("unchecked")
public class SearchUtil {

	public static Path getPath(Root root, String fieldName) {
		String[] subpaths = fieldName.split("\\.");
		Path path = root.get(subpaths[0]);
		for (int i = 1; i < subpaths.length; i++) {
			path = path.get(subpaths[i]);
		}
		return path;
	}

	public static <T> Specification<T> buildSpecification(Class<T> entityType, SearchModel searchModel) {
		if (CollectionUtils.isEmpty(searchModel.getFields()))
			return null;

		List<Predicate> predicates = new ArrayList<>();

		Specification<T> spec = new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				for (SearchField searchField : searchModel.getFields()) {
					Field f = FieldUtils.getField(entityType, searchField.getName(), true);
					Object fieldValue = searchField.getValue();

					// convert LocalDateTime field value to ZonedDateTime
					if (f != null) {
						if (f.getType().isAssignableFrom(ZonedDateTime.class) && fieldValue instanceof LocalDateTime) {
							fieldValue = ZonedDateTime.of((LocalDateTime) fieldValue, ZoneId.systemDefault());
						}
					}

					if (SearchFieldOperator.EQUAL.equals(searchField.getOperator())) {
						Predicate predicate = cb.equal(getPath(root, searchField.getName()), fieldValue);
						predicates.add(predicate);
					}
					else if (SearchFieldOperator.GREATER.equals(searchField.getOperator())) {
						if (fieldValue instanceof Comparable) {
							predicates.add(cb.greaterThan((Expression) getPath(root, searchField.getName()),
									(Comparable) fieldValue));
						}
					}
					else if (SearchFieldOperator.LESS.equals(searchField.getOperator())) {
						if (fieldValue instanceof Comparable) {
							predicates.add(cb.lessThan((Expression) getPath(root, searchField.getName()),
									(Comparable) fieldValue));
						}
					}
					else if (SearchFieldOperator.CONTAINS.equals(searchField.getOperator())) {
						if (fieldValue instanceof String) {
							predicates.add(cb.like((Expression) getPath(root, searchField.getName()),
									"%" + (String) fieldValue + "%"));
						}
					}
					else if (SearchFieldOperator.INTERVAL.equals(searchField.getOperator())) {
						if (!(fieldValue instanceof List)) continue;
						if (CollectionUtils.isEmpty((List) fieldValue)) continue;

						Object startValue = ((List) fieldValue).get(0);
						if (startValue != null && startValue instanceof Comparable) {
							predicates.add(cb.greaterThanOrEqualTo((Expression) getPath(root, searchField.getName()),
									(Comparable) startValue));
						}

						if (((List) fieldValue).size() > 1) {
							Object endValue = ((List) fieldValue).get(1);
							if (endValue != null && endValue instanceof Comparable) {
								predicates.add(cb.lessThanOrEqualTo((Expression) getPath(root, searchField.getName()),
										(Comparable) endValue));
							}
						}
					}
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return spec;
	}
}
