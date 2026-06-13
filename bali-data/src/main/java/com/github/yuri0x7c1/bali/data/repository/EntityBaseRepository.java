package com.github.yuri0x7c1.bali.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityBaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>  {
}
