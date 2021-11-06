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
