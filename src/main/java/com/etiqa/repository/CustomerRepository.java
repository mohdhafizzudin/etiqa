package com.etiqa.repository;

import com.etiqa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, JpaSpecificationExecutor<CustomerEntity> {
}
