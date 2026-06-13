package com.github.yuri0x7c1.bali.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface EntityBaseService<T, ID> {

	/* JpaRepository methods */
	T save(T entity);

	List<T> saveAll(Iterable<T> shifts);

	void delete(T entity);

	void deleteAll(Iterable<T> shifts);

	long count();

	Optional<T> findById(ID id);

	List<T> findAll();

	List<T> findAll(Sort sort);

	List<T> findAllById(Iterable<ID> ids);

	Page<T> findAll(Pageable pageable);
	/* end of JpaRepository methods */

	/* JpaSpecificationExecutor methods */
	Optional<T> findOne(Specification<T> spec);

	List<T> findAll(Specification<T> spec);

	Page<T> findAll(Specification<T> spec, Pageable pageable);

	List<T> findAll(Specification<T> spec, Sort sort);

	long count(Specification<T> spec);
	/* end of JpaSpecificationExecutor methods */
}