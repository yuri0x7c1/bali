package com.github.yuri0x7c1.bali.security.base.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.github.yuri0x7c1.bali.security.domain.SecurityRole;

@Repository
public interface SecurityRoleBaseRepository
		extends
			JpaRepository<SecurityRole, UUID>,
			JpaSpecificationExecutor<SecurityRole> {
}