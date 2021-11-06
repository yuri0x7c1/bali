package com.github.yuri0x7c1.bali.security.base.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.github.yuri0x7c1.bali.security.domain.SecurityGroup;

@Repository
public interface SecurityGroupBaseRepository
		extends
			JpaRepository<SecurityGroup, UUID>,
			JpaSpecificationExecutor<SecurityGroup> {
}