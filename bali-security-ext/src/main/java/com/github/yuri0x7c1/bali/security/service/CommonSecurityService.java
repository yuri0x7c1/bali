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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.yuri0x7c1.bali.security.domain.SecurityGroup;
import com.github.yuri0x7c1.bali.security.domain.SecurityRole;
import com.github.yuri0x7c1.bali.security.domain.SecurityUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonSecurityService {
	@Autowired
	SecurityRoleService securityRoleService;

	@Autowired
	SecurityGroupService securityGroupService;

	@Autowired
	SecurityUserService securityUserService;

	public static final String BCRYPT_PASSWORD = "$2a$10$oO3wWvopMOAUvGiO3uoP7uzS6/u/se0BHgsrxhsWc0gtA9AEiCoIu";

	public static final String ADMIN_USER_NAME = "admin";

	public static final String ADMIN_GROUP_NAME = "Administrators";

	public void createDefaultAdmin(List<String> securityRoles) {
		for (String securityRole : securityRoles) {
			log.debug("Security role name: {}", securityRole);
			SecurityRole role = securityRoleService.findByName(securityRole);
			if (role == null) {
				role = new SecurityRole();
				role.setName(securityRole);
				securityRoleService.save(role);
			}
		}

		SecurityGroup adminGroup = securityGroupService.findByName(ADMIN_GROUP_NAME);
		if (adminGroup == null) {
			adminGroup = new SecurityGroup();
			adminGroup.setName(ADMIN_GROUP_NAME);
		}
		adminGroup.setRoles(securityRoleService.findAll());
		securityGroupService.save(adminGroup);

		SecurityUser adminUser = securityUserService.findByName(ADMIN_USER_NAME);
		if (securityUserService.findByName(ADMIN_USER_NAME) == null) {
			adminUser = new SecurityUser();
			adminUser.setName(ADMIN_USER_NAME);
			adminUser.setPasswordHash(BCRYPT_PASSWORD);
		}
		adminUser.getGroups().add(adminGroup);
		securityUserService.save(adminUser);
	}
}
