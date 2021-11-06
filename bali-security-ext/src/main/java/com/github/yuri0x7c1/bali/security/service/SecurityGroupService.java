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
