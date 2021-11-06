package com.github.yuri0x7c1.bali.security.repository;

import org.springframework.stereotype.Repository;

import com.github.yuri0x7c1.bali.security.base.repository.SecurityUserBaseRepository;
import com.github.yuri0x7c1.bali.security.domain.SecurityUser;

@Repository
public interface SecurityUserRepository extends SecurityUserBaseRepository {
	SecurityUser findByName(String name);
}