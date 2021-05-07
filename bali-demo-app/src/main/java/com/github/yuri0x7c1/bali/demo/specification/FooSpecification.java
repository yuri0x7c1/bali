package com.github.yuri0x7c1.bali.demo.specification;

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
import com.github.yuri0x7c1.bali.demo.domain.Foo;

import lombok.extern.slf4j.Slf4j;

/**
 * Foo Criteria
 */
@Slf4j
public class FooSpecification implements Specification<Foo> {

	QbModel criteria;

	public FooSpecification(QbModel criteria) {
		this.criteria = criteria;
	}

	public Predicate buildPredicate(QbGroup group, Root<Foo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
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
	public Predicate toPredicate(Root<Foo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		return buildPredicate(criteria.getRootGroup(), root, query, builder);
	}
}
