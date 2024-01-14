package com.etiqa.controller;

import com.etiqa.entity.CustomerEntity;
import com.etiqa.response.ApiResponse;
import com.etiqa.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RestController
@RequestMapping("customer")
@AllArgsConstructor
public class CustomerController {
    CustomerService customerService;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> findAll(Pageable page, @RequestParam Optional<Long> customerId,
                                               @RequestParam Optional<String> firstName, @RequestParam Optional<String> lastName,
                                               @RequestParam Optional<String> emailOffice, @RequestParam Optional<String> emailPersonal) {
        return customerService.findAll(page, customerId, firstName, lastName, emailOffice, emailPersonal);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<ApiResponse> findByCustomerId(@PathVariable Long customerId) {
        return customerService.findByCustomerId(customerId);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addCustomer(@RequestBody CustomerEntity request) {
        return customerService.addCustomer(request);
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponse> updateCustomer(@RequestBody CustomerEntity request) {
        return customerService.updateCustomer(request);
    }

    @DeleteMapping("delete/{customerId}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long customerId) {
        return customerService.deleteCustomer(customerId);
    }

}
