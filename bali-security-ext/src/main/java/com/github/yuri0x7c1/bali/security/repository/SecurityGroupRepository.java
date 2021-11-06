package com.github.yuri0x7c1.bali.security.repository;

import org.springframework.stereotype.Repository;

import com.github.yuri0x7c1.bali.security.base.repository.SecurityGroupBaseRepository;
import com.github.yuri0x7c1.bali.security.domain.SecurityGroup;

@Repository
public interface SecurityGroupRepository extends SecurityGroupBaseRepository {
	SecurityGroup findByName(String name);
}