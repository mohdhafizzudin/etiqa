package com.etiqa.service;

import com.etiqa.entity.ProductEntity;
import com.etiqa.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ProductService {
    public ResponseEntity<ApiResponse> findAll(Pageable page, Optional<Long> productId, Optional<String> bookTitle, Optional<Double> bookPrice, Optional<Integer> bookQuantity);

    public ResponseEntity<ApiResponse> findByProductId(Long productId);

    public ResponseEntity<ApiResponse> addProduct(ProductEntity request);

    public ResponseEntity<ApiResponse> updateProduct(ProductEntity request);

    public ResponseEntity<ApiResponse> deleteProduct(Long productId);
}
