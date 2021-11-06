package com.github.yuri0x7c1.bali.security.base.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.github.yuri0x7c1.bali.security.domain.SecurityUser;

@Repository
public interface SecurityUserBaseRepository
		extends
			JpaRepository<SecurityUser, UUID>,
			JpaSpecificationExecutor<SecurityUser> {
}