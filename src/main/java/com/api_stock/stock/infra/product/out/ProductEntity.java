package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.infra.brand.out.BrandEntity;
import com.api_stock.stock.infra.category.out.CategoryEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    @Positive(message = ProductExceptionMessage.NEGATIVE_PRICE)
    private BigDecimal price;

    @Positive(message = ProductExceptionMessage.NEGATIVE_STOCK)
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id",nullable = false)
    private BrandEntity brand;

    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "category_id"})
    )
    @Size(min = ProductConstants.MIN_CATEGORIES_IDS,
            max = ProductConstants.MAX_CATEGORIES_IDS,
            message = ProductExceptionMessage.SIZE_CATEGORIES_ID)
    private Set<CategoryEntity> categories;
}
