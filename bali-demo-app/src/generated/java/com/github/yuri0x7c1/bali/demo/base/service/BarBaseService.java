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

import com.github.yuri0x7c1.bali.demo.base.repository.BarBaseRepository;
import com.github.yuri0x7c1.bali.demo.domain.Bar;

import lombok.extern.slf4j.Slf4j;

/**
 * Bar Base Service
 */
@Slf4j
@Primary
@Service
public class BarBaseService {

	public static final String DEFAULT_ORDER_BY = "id";
	public static final Direction DEFAULT_ORDER_DIRECTION = Direction.ASC;

	protected BarBaseRepository barBaseRepository;

	@Autowired
	public BarBaseService(BarBaseRepository barBaseRepository) {
		this.barBaseRepository = barBaseRepository;
	}

	/* JpaRepository methods */
	@Transactional
	public Bar save(Bar bar) {
		log.trace("Request to save Bar : {}", bar);
		return barBaseRepository.save(bar);
	}

	@Transactional
	public List<Bar> saveAll(Iterable<Bar> bars) {
		log.trace("Request to save Bars : {}", bars);
		return barBaseRepository.saveAll(bars);
	}

	@Transactional
	public void delete(Bar bar) {
		log.trace("Request to delete Bar : {}", bar);
		barBaseRepository.delete(bar);
	}

	@Transactional
	public void deleteAll(Iterable<Bar> bars) {
		log.trace("Request to delete Bars : {}", bars);
		barBaseRepository.deleteAll(bars);
	}

	@Transactional(readOnly=true)
	public long count() {
		log.trace("Request to count all Bars");
		return barBaseRepository.count();
	}

	@Transactional(readOnly=true)
	public Optional<Bar> findById(Integer id) {
		log.trace("Request to get Bar : {}", id);
		return barBaseRepository.findById(id);
	}

	@Transactional(readOnly=true)
	public List<Bar> findAll() {
		log.trace("Request to get all Bars");
		return barBaseRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<Bar> findAll(Sort sort) {
		log.trace("Request to get all Bars");
		return barBaseRepository.findAll(sort);
	}

	@Transactional(readOnly=true)
	public List<Bar> findAllById(Iterable<Integer> ids) {
		log.trace("Request to get all Bars");
		return barBaseRepository.findAllById(ids);
	}

	@Transactional(readOnly=true)
	public Page<Bar> findAll(Pageable pageable) {
		log.trace("Request to get a page of Bars");
		return barBaseRepository.findAll(pageable);
	}
	/* end of JpaRepository methods */

	/* JpaSpecificationExecutor methods */
	@Transactional(readOnly=true)
	public Optional<Bar> findOne(@Nullable Specification<Bar> spec) {
		return barBaseRepository.findOne(spec);
	}

	@Transactional(readOnly=true)
	public List<Bar> findAll(@Nullable Specification<Bar> spec) {
		return barBaseRepository.findAll(spec);
	}

	@Transactional(readOnly=true)
	public Page<Bar> findAll(@Nullable Specification<Bar> spec, Pageable pageable) {
		return barBaseRepository.findAll(spec, pageable);
	}

	@Transactional(readOnly=true)
	public List<Bar> findAll(@Nullable Specification<Bar> spec, Sort sort) {
		return barBaseRepository.findAll(spec, sort);
	}

	@Transactional(readOnly=true)
	public long count(@Nullable Specification<Bar> spec) {
		return barBaseRepository.count(spec);
	}
	/* end of JpaSpecificationExecutor methods */

	@Transactional(readOnly=true)
	public Bar init(Bar bar) {
		log.trace("Initialize lazy fields of Bar : {}", bar);
		bar = barBaseRepository.findById(bar.getId()).get();
		return bar;
	}
}
