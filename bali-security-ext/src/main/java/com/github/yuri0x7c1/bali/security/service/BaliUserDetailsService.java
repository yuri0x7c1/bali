package com.github.yuri0x7c1.bali.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.github.yuri0x7c1.bali.security.domain.SecurityGroup;
import com.github.yuri0x7c1.bali.security.domain.SecurityRole;
import com.github.yuri0x7c1.bali.security.domain.SecurityUser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("userDetailsService")
public class BaliUserDetailsService implements UserDetailsService {
	@Autowired
	private SecurityUserService securityUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Authenticating {}", username);
		SecurityUser securityUser = securityUserService.findByName(username);

		if (securityUser != null) {
			Set<GrantedAuthority> authorities = new HashSet<>();
			for (SecurityGroup securityGroup : securityUserService.init(securityUser).getGroups()) {
				log.debug("{}", securityGroup);
				for (SecurityRole securityRole : securityGroup.getRoles()) {
					authorities.add(new SimpleGrantedAuthority(securityRole.getName()));
				}
			}

			log.debug("User: {}, autorities: {}", securityUser.getName(), authorities);
			return new User(securityUser.getName(), securityUser.getPasswordHash(), authorities);
		}

		String msg = String.format("Unknown user %s", username);
		log.error(msg);
		throw new UsernameNotFoundException(msg);
	}
}