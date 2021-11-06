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