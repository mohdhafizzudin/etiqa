package com.etiqa.service.impl;

import com.etiqa.entity.CustomerEntity;
import com.etiqa.entity.ProductEntity;
import com.etiqa.repository.ProductRepository;
import com.etiqa.response.ApiResponse;
import com.etiqa.service.ProductService;
import com.etiqa.specification.Criteria;
import com.etiqa.specification.SearchCriteria;
import com.etiqa.specification.SpecificationObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    @Override
    public ResponseEntity<ApiResponse> findAll(Pageable page, Optional<Long> productId, Optional<String> bookTitle, Optional<Double> bookPrice, Optional<Integer> bookQuantity) {
        var customer = productRepository.findAll(new SpecificationObject<>(criteria(productId, bookTitle, bookPrice, bookQuantity)), page);
        return ApiResponse.success("products", "Find All Product(s)", customer);
    }

    private List<Criteria> criteria(Optional<Long> productId, Optional<String> bookTitle,
                                    Optional<Double> bookPrice, Optional<Integer> bookQuantity) {
        var criterias = new ArrayList<Criteria>();
        productId.ifPresent(id -> {
            criterias.add(Criteria.builder().key("productId").value(id).operation(SearchCriteria.EQUAL).build());
        });
        bookTitle.ifPresent(bt -> {
            criterias.add(Criteria.builder().key("bookTitle").value(bt).operation(SearchCriteria.MATCH).build());
        });
        bookPrice.ifPresent(bp -> {
            criterias.add(
                    Criteria.builder().key("bookPrice").value(bp).operation(SearchCriteria.EQUAL).build());
        });
        bookQuantity.ifPresent(bq -> {
            criterias.add(
                    Criteria.builder().key("bookQuantity").value(bq).operation(SearchCriteria.EQUAL).build());
        });
        return criterias;
    }

    @Override
    public ResponseEntity<ApiResponse> findByProductId(Long productId) {
        return productRepository.findById(productId).map(customer -> {
            return ApiResponse.success("product", "Find By Product Id", customer);
        }).orElse(ApiResponse.notFound("product"));
    }

    @Override
    public ResponseEntity<ApiResponse> addProduct(ProductEntity request) {
        ProductEntity productEntity = ProductEntity.builder().bookTitle(request.getBookTitle()).
                bookPrice(request.getBookPrice()).bookQuantity(request.getBookQuantity()).build();
        productRepository.save(productEntity);
        return ApiResponse.success("Add Product");
    }

    @Override
    public ResponseEntity<ApiResponse> updateProduct(ProductEntity request) {
        Optional<ProductEntity> productOpt = productRepository.findById(request.getProductId());
        if (productOpt.isPresent()) {
            ProductEntity productEntity = productOpt.get();
            productEntity.setBookTitle(request.getBookTitle());
            productEntity.setBookPrice(request.getBookPrice());
            productEntity.setBookQuantity(request.getBookQuantity());
            productRepository.save(productEntity);
        } else {
            return ApiResponse.notFound("Product");
        }
        return ApiResponse.success("Product Updated");
    }

    @Override
    public ResponseEntity<ApiResponse> deleteProduct(Long productId) {
        Optional<ProductEntity> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            try {
                productRepository.delete(productOpt.get());
                return ApiResponse.success("Product Successfully Delete");
            } catch (Exception e) {
                log.error("Product Deletion Error", e);
                return ApiResponse.exception(e);
            }
        } else {
            return ApiResponse.notFound(productId);
        }
    }
}
