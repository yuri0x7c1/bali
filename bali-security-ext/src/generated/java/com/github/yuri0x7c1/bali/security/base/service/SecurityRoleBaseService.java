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
import com.github.yuri0x7c1.bali.security.base.repository.SecurityRoleBaseRepository;
import com.github.yuri0x7c1.bali.security.domain.SecurityRole;
import com.github.yuri0x7c1.bali.security.specification.SecurityRoleSpecification;

/**
 * SecurityRole Base Service
 */
@Slf4j
@Primary
@Service
public class SecurityRoleBaseService {

	protected SecurityRoleBaseRepository securityRoleBaseRepository;

	@Autowired
	public SecurityRoleBaseService(SecurityRoleBaseRepository securityRoleBaseRepository) {
		this.securityRoleBaseRepository = securityRoleBaseRepository;
	}
	
	/* JpaRepository methods */
	@Transactional
	public SecurityRole save(SecurityRole securityRole) {
		log.debug("Request to save SecurityRole : {}", securityRole);
		return securityRoleBaseRepository.save(securityRole);
	}

	@Transactional
	public List<SecurityRole> saveAll(Iterable<SecurityRole> securityRoles) {
		log.debug("Request to save SecurityRoles : {}", securityRoles);
		return securityRoleBaseRepository.saveAll(securityRoles);
	}

	@Transactional
	public void delete(SecurityRole securityRole) {
		log.debug("Request to delete SecurityRole : {}", securityRole);
		securityRoleBaseRepository.delete(securityRole);
	}

	@Transactional
	public void deleteAll(Iterable<SecurityRole> securityRoles) {
		log.debug("Request to delete SecurityRoles : {}", securityRoles);
		securityRoleBaseRepository.deleteAll(securityRoles);
	}

	@Transactional(readOnly=true)
	public long count() {
		log.debug("Request to count all SecurityRoles");
		return securityRoleBaseRepository.count();
	}

	@Transactional(readOnly=true)
	public Optional<SecurityRole> findById(UUID id) {
		log.debug("Request to get SecurityRole : {}", id);
		return securityRoleBaseRepository.findById(id);
	}

	@Transactional(readOnly=true)
	public List<SecurityRole> findAll() {
		log.debug("Request to get all SecurityRoles");
		return securityRoleBaseRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<SecurityRole> findAll(Sort sort) {
		log.debug("Request to get all SecurityRoles");
		return securityRoleBaseRepository.findAll(sort);
	}

	@Transactional(readOnly=true)
	public List<SecurityRole> findAllById(Iterable<UUID> ids) {
		log.debug("Request to get all SecurityRoles");
		return securityRoleBaseRepository.findAllById(ids);
	}

	@Transactional(readOnly=true)
	public Page<SecurityRole> findAll(Pageable pageable) {
		log.debug("Request to get a page of SecurityRoles");
		return securityRoleBaseRepository.findAll(pageable);
	}
	/* end of JpaRepository methods */
	
	/* JpaSpecificationExecutor methods */
	@Transactional(readOnly=true)
	Optional<SecurityRole> findOne(@Nullable Specification<SecurityRole> spec) {
		return securityRoleBaseRepository.findOne(spec);
	}

	@Transactional(readOnly=true)
	List<SecurityRole> findAll(@Nullable Specification<SecurityRole> spec) {
		return securityRoleBaseRepository.findAll(spec);
	}

	@Transactional(readOnly=true)
	Page<SecurityRole> findAll(@Nullable Specification<SecurityRole> spec, Pageable pageable) {
		return securityRoleBaseRepository.findAll(spec, pageable);
	}

	@Transactional(readOnly=true)
	List<SecurityRole> findAll(@Nullable Specification<SecurityRole> spec, Sort sort) {
		return securityRoleBaseRepository.findAll(spec, sort);
	}

	@Transactional(readOnly=true)
	long count(@Nullable Specification<SecurityRole> spec) {
		return securityRoleBaseRepository.count(spec);
	}
	/* end of JpaSpecificationExecutor methods */
	
	@Transactional(readOnly=true)
	public SecurityRole init(SecurityRole securityRole) {
		log.debug("Initialize lazy fields of SecurityRole : {}", securityRole);
		securityRole = securityRoleBaseRepository.findById(securityRole.getUuid()).get();
		return securityRole;
	}
	
	@Transactional(readOnly=true)
	public Page<SecurityRole> search(QbModel criteria, Pageable pageable) {
		log.debug("Request to search a page of SecurityRoles with criteria: {}", criteria);
		if (criteria != null && criteria.isNotEmpty()) {
			return findAll(new SecurityRoleSpecification(criteria), pageable);
		}
		return securityRoleBaseRepository.findAll(pageable);
	}
	
	@Transactional(readOnly=true)
	public long searchCount(QbModel criteria) {
		log.debug("Request to count all SecurityRoles with criteria: {}", criteria);
		if (criteria != null && criteria.isNotEmpty()) {
			return count(new SecurityRoleSpecification(criteria));
		}
		return securityRoleBaseRepository.count();
	}
}