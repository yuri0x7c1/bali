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

package com.github.yuri0x7c1.bali.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.yuri0x7c1.bali.security.base.service.SecurityRoleBaseService;
import com.github.yuri0x7c1.bali.security.domain.SecurityRole;
import com.github.yuri0x7c1.bali.security.repository.SecurityRoleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecurityRoleService extends SecurityRoleBaseService {

	protected SecurityRoleRepository securityRoleRepository;

	@Autowired
	public SecurityRoleService(SecurityRoleRepository securityRoleRepository) {
		super(securityRoleRepository);
		this.securityRoleRepository = securityRoleRepository;
	}

	@Transactional(readOnly=true)
	public SecurityRole findByName(String name) {
		log.debug("repository: {}", securityRoleRepository);
		return securityRoleRepository.findByName(name);
	}
}
