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

package com.github.yuri0x7c1.bali.security.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.github.yuri0x7c1.bali.data.qb.model.QbCondition;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel;
import com.github.yuri0x7c1.bali.data.qb.model.QbOperator;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel.QbGroup;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel.QbRule;
import com.github.yuri0x7c1.bali.security.domain.SecurityGroup;

import lombok.extern.slf4j.Slf4j;

/**
 * SecurityGroup Criteria
 */
@Slf4j
public class SecurityGroupSpecification implements Specification<SecurityGroup> {

	QbModel criteria;

	public SecurityGroupSpecification(QbModel criteria) {
		this.criteria = criteria;
	}

	public Predicate buildPredicate(QbGroup group, Root<SecurityGroup> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

		List<Predicate> predicates = new ArrayList<>();

		for (QbGroup g : group.getGroups()) {
			return buildPredicate(g, root, query, builder);
		}

		for (QbRule r : group.getRules())  {
			if (QbOperator.EQUAL.equals(r.getOperator())) {
				Predicate predicate = builder.equal(root.get(r.getFieldName()), r.getValue());
				predicates.add(predicate);
			}
			else if (QbOperator.NOT_EQUAL.equals(r.getOperator())) {
				Predicate predicate = builder.notEqual(root.get(r.getFieldName()), r.getValue());
				predicates.add(predicate);
			}
			else if (QbOperator.CONTAINS.equals(r.getOperator())) {
				Predicate predicate = builder.like(root.get(r.getFieldName()), "%" + (String) r.getValue() + "%");
				predicates.add(predicate);
			}
			else if (QbOperator.NOT_CONTAINS.equals(r.getOperator())) {
				Predicate predicate = builder.notLike(root.get(r.getFieldName()), "%" + (String) r.getValue() + "%");
				predicates.add(predicate);
			}
		}

		if (predicates.size() == 0) {
			return null;
		}
		else if (predicates.size() == 1) {
			return predicates.get(0);
		}
		else {
			if (QbCondition.AND.equals(group.getCondition())) {
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			if (QbCondition.OR.equals(group.getCondition())) {
				return builder.or(predicates.toArray(new Predicate[predicates.size()]));
			}
		}

		return null;
	}

	@Override
	public Predicate toPredicate(Root<SecurityGroup> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return buildPredicate(criteria.getRootGroup(), root, query, builder);
	}
}
