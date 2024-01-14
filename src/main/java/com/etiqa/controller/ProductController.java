package com.etiqa.controller;

import com.etiqa.entity.ProductEntity;
import com.etiqa.response.ApiResponse;
import com.etiqa.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("product")
@AllArgsConstructor
public class ProductController {

    ProductService productService;

    @GetMapping("all")
    public ResponseEntity<ApiResponse> findAll(Pageable page, @RequestParam Optional<Long> productId,
                                               @RequestParam Optional<String> bookTitle, @RequestParam Optional<Double> bookPrice,
                                               @RequestParam Optional<Integer> bookQuantity) {
        return productService.findAll(page, productId, bookTitle, bookPrice, bookQuantity);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ApiResponse> findByProductId(@PathVariable Long productId) {
        return productService.findByProductId(productId);
    }

    @PostMapping("add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductEntity request) {
        return productService.addProduct(request);
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductEntity request) {
        return productService.updateProduct(request);
    }

    @DeleteMapping("delete/{productId}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

}
