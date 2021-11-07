package com.github.yuri0x7c1.bali.demo.base.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.yuri0x7c1.bali.demo.base.repository.FooBaseRepository;
import com.github.yuri0x7c1.bali.demo.domain.Foo;

import lombok.extern.slf4j.Slf4j;

/**
 * Foo Base Service
 */
@Slf4j
@Primary
@Service
public class FooBaseService {

	public static final String DEFAULT_ORDER_BY = "id";
	public static final Direction DEFAULT_ORDER_DIRECTION = Direction.ASC;

	protected FooBaseRepository fooBaseRepository;

	@Autowired
	public FooBaseService(FooBaseRepository fooBaseRepository) {
		this.fooBaseRepository = fooBaseRepository;
	}

	/* JpaRepository methods */
	@Transactional
	public Foo save(Foo foo) {
		log.trace("Request to save Foo : {}", foo);
		return fooBaseRepository.save(foo);
	}

	@Transactional
	public List<Foo> saveAll(Iterable<Foo> foos) {
		log.trace("Request to save Foos : {}", foos);
		return fooBaseRepository.saveAll(foos);
	}

	@Transactional
	public void delete(Foo foo) {
		log.trace("Request to delete Foo : {}", foo);
		fooBaseRepository.delete(foo);
	}

	@Transactional
	public void deleteAll(Iterable<Foo> foos) {
		log.trace("Request to delete Foos : {}", foos);
		fooBaseRepository.deleteAll(foos);
	}

	@Transactional(readOnly=true)
	public long count() {
		log.trace("Request to count all Foos");
		return fooBaseRepository.count();
	}

	@Transactional(readOnly=true)
	public Optional<Foo> findById(Integer id) {
		log.trace("Request to get Foo : {}", id);
		return fooBaseRepository.findById(id);
	}

	@Transactional(readOnly=true)
	public List<Foo> findAll() {
		log.trace("Request to get all Foos");
		return fooBaseRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Foo> findAll(Sort sort) {
		log.trace("Request to get all Foos");
		return fooBaseRepository.findAll(sort);
	}

	@Transactional(readOnly=true)
	public List<Foo> findAllById(Iterable<Integer> ids) {
		log.trace("Request to get all Foos");
		return fooBaseRepository.findAllById(ids);
	}

	@Transactional(readOnly=true)
	public Page<Foo> findAll(Pageable pageable) {
		log.trace("Request to get a page of Foos");
		return fooBaseRepository.findAll(pageable);
	}
	/* end of JpaRepository methods */

	/* JpaSpecificationExecutor methods */
	@Transactional(readOnly=true)
	public Optional<Foo> findOne(@Nullable Specification<Foo> spec) {
		return fooBaseRepository.findOne(spec);
	}

	@Transactional(readOnly=true)
	public List<Foo> findAll(@Nullable Specification<Foo> spec) {
		return fooBaseRepository.findAll(spec);
	}

	@Transactional(readOnly=true)
	public Page<Foo> findAll(@Nullable Specification<Foo> spec, Pageable pageable) {
		return fooBaseRepository.findAll(spec, pageable);
	}

	@Transactional(readOnly=true)
	public List<Foo> findAll(@Nullable Specification<Foo> spec, Sort sort) {
		return fooBaseRepository.findAll(spec, sort);
	}

	@Transactional(readOnly=true)
	public long count(@Nullable Specification<Foo> spec) {
		return fooBaseRepository.count(spec);
	}
	/* end of JpaSpecificationExecutor methods */

	@Transactional(readOnly=true)
	public Foo init(Foo foo) {
		log.trace("Initialize lazy fields of Foo : {}", foo);
		foo = fooBaseRepository.findById(foo.getId()).get();
		if (foo.getLinkedBars() != null) foo.getLinkedBars().size();
		return foo;
	}
}
