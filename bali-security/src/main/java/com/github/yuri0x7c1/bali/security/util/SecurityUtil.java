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

package com.github.yuri0x7c1.bali.security.util;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {
	public static boolean check(String... roles) {
		try {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication authentication = securityContext.getAuthentication();
			Collection<? extends GrantedAuthority> authorities  = authentication.getAuthorities();
			log.debug("User: {}, authorities: {}, roles: {}", authentication.getName(), authorities, roles);
			for (String role : Arrays.asList(roles)) {
				for (GrantedAuthority authority : authorities) {
					if (role.equals(authority.getAuthority())) {
						return true;
					}
				}
			}
		}
		catch (Exception e) {
			log.debug("Error checking roles", e);
		}

		return false;
	}
}