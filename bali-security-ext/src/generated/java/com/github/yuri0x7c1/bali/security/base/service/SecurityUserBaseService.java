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

package com.github.yuri0x7c1.bali.security.base.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import com.github.yuri0x7c1.bali.data.qb.model.QbModel;
import com.github.yuri0x7c1.bali.security.base.repository.SecurityUserBaseRepository;
import com.github.yuri0x7c1.bali.security.domain.SecurityUser;
import com.github.yuri0x7c1.bali.security.specification.SecurityUserSpecification;

/**
 * SecurityUser Base Service
 */
@Slf4j
@Primary
@Service
public class SecurityUserBaseService {

	protected SecurityUserBaseRepository securityUserBaseRepository;

	@Autowired
	public SecurityUserBaseService(SecurityUserBaseRepository securityUserBaseRepository) {
		this.securityUserBaseRepository = securityUserBaseRepository;
	}
	
	/* JpaRepository methods */
	@Transactional
	public SecurityUser save(SecurityUser securityUser) {
		log.debug("Request to save SecurityUser : {}", securityUser);
		return securityUserBaseRepository.save(securityUser);
	}

	@Transactional
	public List<SecurityUser> saveAll(Iterable<SecurityUser> securityUsers) {
		log.debug("Request to save SecurityUsers : {}", securityUsers);
		return securityUserBaseRepository.saveAll(securityUsers);
	}

	@Transactional
	public void delete(SecurityUser securityUser) {
		log.debug("Request to delete SecurityUser : {}", securityUser);
		securityUserBaseRepository.delete(securityUser);
	}

	@Transactional
	public void deleteAll(Iterable<SecurityUser> securityUsers) {
		log.debug("Request to delete SecurityUsers : {}", securityUsers);
		securityUserBaseRepository.deleteAll(securityUsers);
	}

	@Transactional(readOnly=true)
	public long count() {
		log.debug("Request to count all SecurityUsers");
		return securityUserBaseRepository.count();
	}

	@Transactional(readOnly=true)
	public Optional<SecurityUser> findById(UUID id) {
		log.debug("Request to get SecurityUser : {}", id);
		return securityUserBaseRepository.findById(id);
	}

	@Transactional(readOnly=true)
	public List<SecurityUser> findAll() {
		log.debug("Request to get all SecurityUsers");
		return securityUserBaseRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<SecurityUser> findAll(Sort sort) {
		log.debug("Request to get all SecurityUsers");
		return securityUserBaseRepository.findAll(sort);
	}

	@Transactional(readOnly=true)
	public List<SecurityUser> findAllById(Iterable<UUID> ids) {
		log.debug("Request to get all SecurityUsers");
		return securityUserBaseRepository.findAllById(ids);
	}

	@Transactional(readOnly=true)
	public Page<SecurityUser> findAll(Pageable pageable) {
		log.debug("Request to get a page of SecurityUsers");
		return securityUserBaseRepository.findAll(pageable);
	}
	/* end of JpaRepository methods */
	
	/* JpaSpecificationExecutor methods */
	@Transactional(readOnly=true)
	Optional<SecurityUser> findOne(@Nullable Specification<SecurityUser> spec) {
		return securityUserBaseRepository.findOne(spec);
	}

	@Transactional(readOnly=true)
	List<SecurityUser> findAll(@Nullable Specification<SecurityUser> spec) {
		return securityUserBaseRepository.findAll(spec);
	}

	@Transactional(readOnly=true)
	Page<SecurityUser> findAll(@Nullable Specification<SecurityUser> spec, Pageable pageable) {
		return securityUserBaseRepository.findAll(spec, pageable);
	}

	@Transactional(readOnly=true)
	List<SecurityUser> findAll(@Nullable Specification<SecurityUser> spec, Sort sort) {
		return securityUserBaseRepository.findAll(spec, sort);
	}

	@Transactional(readOnly=true)
	long count(@Nullable Specification<SecurityUser> spec) {
		return securityUserBaseRepository.count(spec);
	}
	/* end of JpaSpecificationExecutor methods */
	
	@Transactional(readOnly=true)
	public SecurityUser init(SecurityUser securityUser) {
		log.debug("Initialize lazy fields of SecurityUser : {}", securityUser);
		securityUser = securityUserBaseRepository.findById(securityUser.getUuid()).get();
		return securityUser;
	}
	
	@Transactional(readOnly=true)
	public Page<SecurityUser> search(QbModel criteria, Pageable pageable) {
		log.debug("Request to search a page of SecurityUsers with criteria: {}", criteria);
		if (criteria != null && criteria.isNotEmpty()) {
			return findAll(new SecurityUserSpecification(criteria), pageable);
		}
		return securityUserBaseRepository.findAll(pageable);
	}
	
	@Transactional(readOnly=true)
	public long searchCount(QbModel criteria) {
		log.debug("Request to count all SecurityUsers with criteria: {}", criteria);
		if (criteria != null && criteria.isNotEmpty()) {
			return count(new SecurityUserSpecification(criteria));
		}
		return securityUserBaseRepository.count();
	}
}