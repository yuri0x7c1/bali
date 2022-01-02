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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.yuri0x7c1.bali.security.base.service.SecurityUserBaseService;
import com.github.yuri0x7c1.bali.security.domain.SecurityUser;
import com.github.yuri0x7c1.bali.security.repository.SecurityUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SecurityUserService extends SecurityUserBaseService {
	private final SecurityUserRepository securityUserRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public SecurityUserService(SecurityUserRepository securityUserRepository, PasswordEncoder passwordEncoder) {
		super(securityUserRepository);
		this.securityUserRepository = securityUserRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly=true)
	public SecurityUser findByName(String name) {
		return securityUserRepository.findByName(name);
	}

	@Override
	@Transactional
	public SecurityUser save(SecurityUser securityUser) {
		if(StringUtils.isNotBlank(securityUser.getPassword())) {
			if (!securityUser.getPassword().equals(securityUser.getPasswordConfirmation())) {
				String msg = "Password and password confirmation not match!";
				log.error(msg);
				throw new RuntimeException(msg);
			}
			else {
				securityUser.setPassword(passwordEncoder.encode(securityUser.getPassword()));
			}
		}

		return super.save(securityUser);
	}
}
