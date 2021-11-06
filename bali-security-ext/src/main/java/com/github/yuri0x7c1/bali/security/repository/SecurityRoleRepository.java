package com.github.yuri0x7c1.bali.security.repository;

import org.springframework.stereotype.Repository;

import com.github.yuri0x7c1.bali.security.base.repository.SecurityRoleBaseRepository;
import com.github.yuri0x7c1.bali.security.domain.SecurityRole;

@Repository
public interface SecurityRoleRepository extends SecurityRoleBaseRepository {
	SecurityRole findByName(String name);
}