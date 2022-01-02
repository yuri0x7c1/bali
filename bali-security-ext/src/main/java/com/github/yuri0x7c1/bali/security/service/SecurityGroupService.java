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

import com.github.yuri0x7c1.bali.security.base.service.SecurityGroupBaseService;
import com.github.yuri0x7c1.bali.security.domain.SecurityGroup;
import com.github.yuri0x7c1.bali.security.repository.SecurityGroupRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecurityGroupService extends SecurityGroupBaseService {

	protected SecurityGroupRepository securityGroupRepository;

	@Autowired
	public SecurityGroupService(SecurityGroupRepository securityGroupRepository) {
		super(securityGroupRepository);
		this.securityGroupRepository = securityGroupRepository;
	}

	@Transactional(readOnly=true)
	public SecurityGroup findByName(String name) {
		return securityGroupRepository.findByName(name);
	}
}
