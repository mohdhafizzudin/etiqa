package com.etiqa.service.impl;

import com.etiqa.entity.CustomerEntity;
import com.etiqa.repository.CustomerRepository;
import com.etiqa.response.ApiResponse;
import com.etiqa.service.CustomerService;
import com.etiqa.specification.Criteria;
import com.etiqa.specification.SearchCriteria;
import com.etiqa.specification.SpecificationObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;

    @Override
    public ResponseEntity<ApiResponse> findAll(Pageable page, Optional<Long> customerId, Optional<String> firstName, Optional<String> lastName, Optional<String> emailOffice, Optional<String> emailPersonal) {
        var customer = customerRepository.findAll(new SpecificationObject<>(criteria(customerId, firstName, lastName, emailOffice, emailPersonal)), page);
        return ApiResponse.success("customers", "Find All Customer(s)", customer);
    }

    private List<Criteria> criteria(Optional<Long> customerId, Optional<String> firstName,
                                    Optional<String> lastName, Optional<String> emailOffice, Optional<String> emailPersonal) {
        var criterias = new ArrayList<Criteria>();
        customerId.ifPresent(id -> {
            criterias.add(Criteria.builder().key("customerId").value(id).operation(SearchCriteria.EQUAL).build());
        });
        firstName.ifPresent(fname -> {
            criterias.add(Criteria.builder().key("firstName").value(fname).operation(SearchCriteria.MATCH).build());
        });
        lastName.ifPresent(lname -> {
            criterias.add(
                    Criteria.builder().key("lastName").value(lname).operation(SearchCriteria.MATCH).build());
        });
        emailOffice.ifPresent(eoffice -> {
            criterias.add(
                    Criteria.builder().key("emailOffice").value(eoffice).operation(SearchCriteria.MATCH).build());
        });
        emailPersonal.ifPresent(epersonal -> {
            criterias.add(
                    Criteria.builder().key("emailPersonal").value(epersonal).operation(SearchCriteria.MATCH).build());
        });
        return criterias;
    }

    @Override
    public ResponseEntity<ApiResponse> findByCustomerId(Long customerId) {
        return customerRepository.findById(customerId).map(customer -> {
            return ApiResponse.success("customer", "Find By customer Id", customer);
        }).orElse(ApiResponse.notFound("customer"));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> addCustomer(CustomerEntity request) {
        CustomerEntity customerEntity = CustomerEntity.builder().firstName(request.getFirstName()).
                lastName(request.getLastName()).emailOffice(request.getEmailOffice()).emailPersonal(request.getEmailPersonal()).build();
        customerRepository.save(customerEntity);
        return ApiResponse.success("Add Customer");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> updateCustomer(CustomerEntity request) {
        Optional<CustomerEntity> customerOpt = customerRepository.findById(request.getCustomerId());
        if (customerOpt.isPresent()) {
            CustomerEntity customerEntity = customerOpt.get();
            customerEntity.setFirstName(request.getFirstName());
            customerEntity.setLastName(request.getLastName());
            customerEntity.setEmailOffice(request.getEmailOffice());
            customerEntity.setEmailPersonal(request.getEmailPersonal());
            customerRepository.save(customerEntity);
        } else {
            return ApiResponse.notFound("Customer");
        }
        return ApiResponse.success("Customer Updated");
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> deleteCustomer(Long customerId) {
        Optional<CustomerEntity> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isPresent()) {
            try {
                customerRepository.delete(customerOpt.get());
                return ApiResponse.success("Customer Successfully Delete");
            } catch (Exception e) {
                log.error("Customer Deletion Error", e);
                return ApiResponse.exception(e);
            }
        } else {
            return ApiResponse.notFound(customerId);
        }
    }
}
