package com.github.yuri0x7c1.bali.demo.base.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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

import lombok.extern.slf4j.Slf4j;
import com.github.yuri0x7c1.bali.demo.base.repository.FooBaseRepository;
import com.github.yuri0x7c1.bali.demo.specification.FooSpecification;
import com.github.yuri0x7c1.bali.data.qb.model.QbModel;

import com.github.yuri0x7c1.bali.demo.domain.Foo;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import com.github.yuri0x7c1.bali.demo.domain.Bar;

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

	@Transactional(readOnly=true)
	public Page<Foo> search(QbModel criteria, Pageable pageable) {
		log.trace("Request to search a page of Foos with criteria: {}", criteria);
		if (criteria != null && criteria.isNotEmpty()) {
			return findAll(new FooSpecification(criteria), pageable);
		}
		return fooBaseRepository.findAll(pageable);
	}

	@Transactional(readOnly=true)
	public long searchCount(QbModel criteria) {
		log.trace("Request to count all Foos with criteria: {}", criteria);
		if (criteria != null && criteria.isNotEmpty()) {
			return count(new FooSpecification(criteria));
		}
		return fooBaseRepository.count();
	}

	/* Finder methods for entity fields */
	/* stringValue */
	@Transactional(readOnly=true)
	public Page<Foo> findByStringValue(String stringValue, Pageable pageable) {
		return fooBaseRepository.findByStringValue(stringValue, pageable);
	}

	@Transactional(readOnly=true)
	public long countByStringValue(String stringValue) {
		return fooBaseRepository.countByStringValue(stringValue);
	}

	@Transactional(readOnly=true)
	public Page<Foo> findByStringValueLike(String stringValue, Pageable pageable) {
		return fooBaseRepository.findByStringValueLike(stringValue, pageable);
	}

	@Transactional(readOnly=true)
	public long countByStringValueLike(String stringValue) {
		return fooBaseRepository.countByStringValueLike(stringValue);
	}

	@Transactional(readOnly=true)
	public Page<Foo> findByStringValueLikeIgnoreCase(String stringValue, Pageable pageable) {
		return fooBaseRepository.findByStringValueLikeIgnoreCase(stringValue, pageable);
	}

	@Transactional(readOnly=true)
	public long countByStringValueLikeIgnoreCase(String stringValue) {
		return fooBaseRepository.countByStringValueLikeIgnoreCase(stringValue);
	}

	@Transactional(readOnly=true)
	public Page<Foo> findByStringValueContaining(String stringValue, Pageable pageable) {
		if (StringUtils.isBlank(stringValue)) {
			return fooBaseRepository.findAll(pageable);
		}
		return fooBaseRepository.findByStringValueContaining(stringValue, pageable);
	}

	@Transactional(readOnly=true)
	public long countByStringValueContaining(String stringValue) {
		if (StringUtils.isBlank(stringValue)) {
			return fooBaseRepository.count();
		}
		return fooBaseRepository.countByStringValueContaining(stringValue);
	}

	@Transactional(readOnly=true)
	public Page<Foo> findByStringValueContainingIgnoreCase(String stringValue, Pageable pageable) {
		if (StringUtils.isBlank(stringValue)) {
			return fooBaseRepository.findAll(pageable);
		}
		return fooBaseRepository.findByStringValueContainingIgnoreCase(stringValue, pageable);
	}

	@Transactional(readOnly=true)
	public long countByStringValueContainingIgnoreCase(String stringValue) {
		if (StringUtils.isBlank(stringValue)) {
			return fooBaseRepository.count();
		}
		return fooBaseRepository.countByStringValueContainingIgnoreCase(stringValue);
	}

	/* longValue */
	@Transactional(readOnly=true)
	public Page<Foo> findByLongValue(Long longValue, Pageable pageable) {
		return fooBaseRepository.findByLongValue(longValue, pageable);
	}

	@Transactional(readOnly=true)
	public long countByLongValue(Long longValue) {
		return fooBaseRepository.countByLongValue(longValue);
	}

	/* doubleValue */
	@Transactional(readOnly=true)
	public Page<Foo> findByDoubleValue(Double doubleValue, Pageable pageable) {
		return fooBaseRepository.findByDoubleValue(doubleValue, pageable);
	}

	@Transactional(readOnly=true)
	public long countByDoubleValue(Double doubleValue) {
		return fooBaseRepository.countByDoubleValue(doubleValue);
	}

	/* booleanValue */
	@Transactional(readOnly=true)
	public Page<Foo> findByBooleanValue(Boolean booleanValue, Pageable pageable) {
		return fooBaseRepository.findByBooleanValue(booleanValue, pageable);
	}

	@Transactional(readOnly=true)
	public long countByBooleanValue(Boolean booleanValue) {
		return fooBaseRepository.countByBooleanValue(booleanValue);
	}

	/* date */
	@Transactional(readOnly=true)
	public Page<Foo> findByDate(Date date, Pageable pageable) {
		return fooBaseRepository.findByDate(date, pageable);
	}

	@Transactional(readOnly=true)
	public long countByDate(Date date) {
		return fooBaseRepository.countByDate(date);
	}

	/* instant */
	@Transactional(readOnly=true)
	public Page<Foo> findByInstant(Instant instant, Pageable pageable) {
		return fooBaseRepository.findByInstant(instant, pageable);
	}

	@Transactional(readOnly=true)
	public long countByInstant(Instant instant) {
		return fooBaseRepository.countByInstant(instant);
	}

	/* localDateTime */
	@Transactional(readOnly=true)
	public Page<Foo> findByLocalDateTime(LocalDateTime localDateTime, Pageable pageable) {
		return fooBaseRepository.findByLocalDateTime(localDateTime, pageable);
	}

	@Transactional(readOnly=true)
	public long countByLocalDateTime(LocalDateTime localDateTime) {
		return fooBaseRepository.countByLocalDateTime(localDateTime);
	}

	/* zonedDateTime */
	@Transactional(readOnly=true)
	public Page<Foo> findByZonedDateTime(ZonedDateTime zonedDateTime, Pageable pageable) {
		return fooBaseRepository.findByZonedDateTime(zonedDateTime, pageable);
	}

	@Transactional(readOnly=true)
	public long countByZonedDateTime(ZonedDateTime zonedDateTime) {
		return fooBaseRepository.countByZonedDateTime(zonedDateTime);
	}

	/* localDate */
	@Transactional(readOnly=true)
	public Page<Foo> findByLocalDate(LocalDate localDate, Pageable pageable) {
		return fooBaseRepository.findByLocalDate(localDate, pageable);
	}

	@Transactional(readOnly=true)
	public long countByLocalDate(LocalDate localDate) {
		return fooBaseRepository.countByLocalDate(localDate);
	}

	/* bar */
	@Transactional(readOnly=true)
	public Page<Foo> findByBar(Bar bar, Pageable pageable) {
		return fooBaseRepository.findByBar(bar, pageable);
	}

	@Transactional(readOnly=true)
	public long countByBar(Bar bar) {
		return fooBaseRepository.countByBar(bar);
	}

}
