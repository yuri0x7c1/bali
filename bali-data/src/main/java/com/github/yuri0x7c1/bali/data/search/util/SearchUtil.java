package com.github.yuri0x7c1.bali.data.search.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
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
					if (SearchFieldOperator.EQUAL.equals(searchField.getOperator())) {
						Predicate predicate = cb.equal(getPath(root, searchField.getName()), searchField.getValue());
						predicates.add(predicate);
					}
					else if (SearchFieldOperator.GREATER.equals(searchField.getOperator())) {
						if (searchField.getValue() instanceof Comparable) {
							predicates.add(cb.greaterThan((Expression) getPath(root, searchField.getName()),
									(Comparable) searchField.getValue()));
						}
					} else if (SearchFieldOperator.LESS.equals(searchField.getOperator())) {
						if (searchField.getValue() instanceof Comparable) {
							predicates.add(cb.lessThan((Expression) getPath(root, searchField.getName()),
									(Comparable) searchField.getValue()));
						}
					}
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return spec;
	}
}
