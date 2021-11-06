package com.github.yuri0x7c1.bali.data.search.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
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
public class SearchUtil {
	public static <T> Specification<T> buildSpecification(Class<T> entityType, SearchModel searchModel) {
		if (CollectionUtils.isEmpty(searchModel.getFields()))
			return null;

		MultiValuedMap<String, Predicate> predicateMap = new ArrayListValuedHashMap<>();

		Specification<T> spec = new Specification<T>() {
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				for (SearchField searchField : searchModel.getFields()) {
					if (SearchFieldOperator.EQUAL.equals(searchField.getOperator())) {
						Predicate predicate = cb.equal(root.get(searchField.getName()), searchField.getValue());
						predicateMap.put(searchField.getName(), predicate);
					}
				}

				List<Predicate> predicates = new ArrayList<>();
				for (String fieldName : predicateMap.keySet()) {
					Collection<Predicate> predicatesForFieldName = predicateMap.get(fieldName);
					predicates.add(cb.or(predicatesForFieldName.toArray(new Predicate[predicatesForFieldName.size()])));
				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return spec;
	}
}
