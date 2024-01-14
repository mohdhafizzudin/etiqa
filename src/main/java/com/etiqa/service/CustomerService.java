package com.etiqa.service;

import com.etiqa.entity.CustomerEntity;
import com.etiqa.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CustomerService {
    public ResponseEntity<ApiResponse> findAll(Pageable page, Optional<Long> customerId, Optional<String> firstName, Optional<String> lastName, Optional<String> emailOffice, Optional<String> emailPersonal);

    public ResponseEntity<ApiResponse> findByCustomerId(Long customerId);

    public ResponseEntity<ApiResponse> addCustomer(CustomerEntity request);

    public ResponseEntity<ApiResponse> updateCustomer(CustomerEntity request);

    public ResponseEntity<ApiResponse> deleteCustomer(Long customerId);
}
