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
import com.github.yuri0x7c1.bali.security.base.repository.SecurityGroupBaseRepository;
import com.github.yuri0x7c1.bali.security.domain.SecurityGroup;
import com.github.yuri0x7c1.bali.security.specification.SecurityGroupSpecification;

/**
 * SecurityGroup Base Service
 */
@Slf4j
@Primary
@Service
public class SecurityGroupBaseService {

	protected SecurityGroupBaseRepository securityGroupBaseRepository;

	@Autowired
	public SecurityGroupBaseService(SecurityGroupBaseRepository securityGroupBaseRepository) {
		this.securityGroupBaseRepository = securityGroupBaseRepository;
	}
	
	/* JpaRepository methods */
	@Transactional
	public SecurityGroup save(SecurityGroup securityGroup) {
		log.debug("Request to save SecurityGroup : {}", securityGroup);
		return securityGroupBaseRepository.save(securityGroup);
	}

	@Transactional
	public List<SecurityGroup> saveAll(Iterable<SecurityGroup> securityGroups) {
		log.debug("Request to save SecurityGroups : {}", securityGroups);
		return securityGroupBaseRepository.saveAll(securityGroups);
	}

	@Transactional
	public void delete(SecurityGroup securityGroup) {
		log.debug("Request to delete SecurityGroup : {}", securityGroup);
		securityGroupBaseRepository.delete(securityGroup);
	}

	@Transactional
	public void deleteAll(Iterable<SecurityGroup> securityGroups) {
		log.debug("Request to delete SecurityGroups : {}", securityGroups);
		securityGroupBaseRepository.deleteAll(securityGroups);
	}

	@Transactional(readOnly=true)
	public long count() {
		log.debug("Request to count all SecurityGroups");
		return securityGroupBaseRepository.count();
	}

	@Transactional(readOnly=true)
	public Optional<SecurityGroup> findById(UUID id) {
		log.debug("Request to get SecurityGroup : {}", id);
		return securityGroupBaseRepository.findById(id);
	}

	@Transactional(readOnly=true)
	public List<SecurityGroup> findAll() {
		log.debug("Request to get all SecurityGroups");
		return securityGroupBaseRepository.findAll();
	}

	@Transactional(readOnly=true)
	public List<SecurityGroup> findAll(Sort sort) {
		log.debug("Request to get all SecurityGroups");
		return securityGroupBaseRepository.findAll(sort);
	}

	@Transactional(readOnly=true)
	public List<SecurityGroup> findAllById(Iterable<UUID> ids) {
		log.debug("Request to get all SecurityGroups");
		return securityGroupBaseRepository.findAllById(ids);
	}

	@Transactional(readOnly=true)
	public Page<SecurityGroup> findAll(Pageable pageable) {
		log.debug("Request to get a page of SecurityGroups");
		return securityGroupBaseRepository.findAll(pageable);
	}
	/* end of JpaRepository methods */
	
	/* JpaSpecificationExecutor methods */
	@Transactional(readOnly=true)
	Optional<SecurityGroup> findOne(@Nullable Specification<SecurityGroup> spec) {
		return securityGroupBaseRepository.findOne(spec);
	}

	@Transactional(readOnly=true)
	List<SecurityGroup> findAll(@Nullable Specification<SecurityGroup> spec) {
		return securityGroupBaseRepository.findAll(spec);
	}

	@Transactional(readOnly=true)
	Page<SecurityGroup> findAll(@Nullable Specification<SecurityGroup> spec, Pageable pageable) {
		return securityGroupBaseRepository.findAll(spec, pageable);
	}

	@Transactional(readOnly=true)
	List<SecurityGroup> findAll(@Nullable Specification<SecurityGroup> spec, Sort sort) {
		return securityGroupBaseRepository.findAll(spec, sort);
	}

	@Transactional(readOnly=true)
	long count(@Nullable Specification<SecurityGroup> spec) {
		return securityGroupBaseRepository.count(spec);
	}
	/* end of JpaSpecificationExecutor methods */
	
	@Transactional(readOnly=true)
	public SecurityGroup init(SecurityGroup securityGroup) {
		log.debug("Initialize lazy fields of SecurityGroup : {}", securityGroup);
		securityGroup = securityGroupBaseRepository.findById(securityGroup.getUuid()).get();
		return securityGroup;
	}
	
	@Transactional(readOnly=true)
	public Page<SecurityGroup> search(QbModel criteria, Pageable pageable) {
		log.debug("Request to search a page of SecurityGroups with criteria: {}", criteria);
		if (criteria != null && criteria.isNotEmpty()) {
			return findAll(new SecurityGroupSpecification(criteria), pageable);
		}
		return securityGroupBaseRepository.findAll(pageable);
	}
	
	@Transactional(readOnly=true)
	public long searchCount(QbModel criteria) {
		log.debug("Request to count all SecurityGroups with criteria: {}", criteria);
		if (criteria != null && criteria.isNotEmpty()) {
			return count(new SecurityGroupSpecification(criteria));
		}
		return securityGroupBaseRepository.count();
	}
}