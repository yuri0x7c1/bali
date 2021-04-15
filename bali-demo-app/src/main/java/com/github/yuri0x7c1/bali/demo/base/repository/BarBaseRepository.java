package com.github.yuri0x7c1.bali.demo.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.github.yuri0x7c1.bali.demo.domain.Bar;

/**
 * Bar Base Repository
 */
@Repository
public interface BarBaseRepository extends JpaRepository<Bar, Integer>, JpaSpecificationExecutor<Bar> {
	/* Finder methods for entity fields */
	/* value */
	Page<Bar> findByValue(String value, Pageable pageable);

	long countByValue(String value);

	Page<Bar> findByValueLike(String value, Pageable pageable);

	long countByValueLike(String value);

	Page<Bar> findByValueLikeIgnoreCase(String value, Pageable pageable);

	long countByValueLikeIgnoreCase(String value);

	Page<Bar> findByValueContaining(String value, Pageable pageable);

	long countByValueContaining(String value);

	Page<Bar> findByValueContainingIgnoreCase(String value, Pageable pageable);

	long countByValueContainingIgnoreCase(String value);

}
