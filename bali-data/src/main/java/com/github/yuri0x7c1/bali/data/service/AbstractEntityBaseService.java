package com.github.yuri0x7c1.bali.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.github.yuri0x7c1.bali.data.repository.EntityBaseRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Entity Base Service
 */
@Slf4j
public abstract class AbstractEntityBaseService<T, ID> implements EntityBaseService<T, ID> {

	private EntityBaseRepository<T, ID> entityBaseRepository;

	public AbstractEntityBaseService(EntityBaseRepository<T, ID> entityBaseRepository) {
		this.entityBaseRepository = entityBaseRepository;
	}

	/* JpaRepository methods */
	@Override
	@Transactional
	public T save(T entity) {
		log.trace("Request to save entity : {}", entity);
		return entityBaseRepository.save(entity);
	}

	@Override
	@Transactional
	public List<T> saveAll(Iterable<T> shifts) {
		log.trace("Request to save entities : {}", shifts);
		return entityBaseRepository.saveAll(shifts);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		log.trace("Request to delete entity : {}", entity);
		entityBaseRepository.delete(entity);
	}

	@Override
	@Transactional
	public void deleteAll(Iterable<T> shifts) {
		log.trace("Request to delete entities : {}", shifts);
		entityBaseRepository.deleteAll(shifts);
	}

	@Override
	@Transactional(readOnly=true)
	public long count() {
		log.trace("Request to count all entities");
		return entityBaseRepository.count();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<T> findById(ID id) {
		log.trace("Request to get entity : {}", id);
		return entityBaseRepository.findById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> findAll() {
		log.trace("Request to get all entities");
		return entityBaseRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> findAll(Sort sort) {
		log.trace("Request to get all entities");
		return entityBaseRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> findAllById(Iterable<ID> ids) {
		log.trace("Request to get all entities");
		return entityBaseRepository.findAllById(ids);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<T> findAll(Pageable pageable) {
		log.trace("Request to get a page of entities");
		return entityBaseRepository.findAll(pageable);
	}
	/* end of JpaRepository methods */

	/* JpaSpecificationExecutor methods */
	@Override
	@Transactional(readOnly=true)
	public Optional<T> findOne(@Nullable Specification<T> spec) {
		return entityBaseRepository.findOne(spec);
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> findAll(@Nullable Specification<T> spec) {
		return entityBaseRepository.findAll(spec);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable) {
		return entityBaseRepository.findAll(spec, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public List<T> findAll(@Nullable Specification<T> spec, Sort sort) {
		return entityBaseRepository.findAll(spec, sort);
	}

	@Override
	@Transactional(readOnly=true)
	public long count(@Nullable Specification<T> spec) {
		return entityBaseRepository.count(spec);
	}
	/* end of JpaSpecificationExecutor methods */
}
