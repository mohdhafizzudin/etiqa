package com.etiqa.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "product", schema = "public")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", sequenceName = "public.product_id_seq", allocationSize = 1)
    Long productId;
    String bookTitle;
    Double bookPrice;
    int bookQuantity;

    @Builder
    public ProductEntity(Long productId, String bookTitle, Double bookPrice, int bookQuantity) {
        this.productId = productId;
        this.bookTitle = bookTitle;
        this.bookPrice = bookPrice;
        this.bookQuantity = bookQuantity;
    }
}
