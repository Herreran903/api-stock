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

import static com.api_stock.stock.domain.product.util.ProductConstants.*;

@Entity
@Table(name = PRODUCT_TABLE_NAME)
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
    @JoinColumn(name = BRAND_ID_COLUMN ,nullable = false)
    private BrandEntity brand;

    @ManyToMany
    @JoinTable(
            name = PRODUCT_CATEGORIES_TABLE_NAME,
            joinColumns = @JoinColumn(name = PRODUCT_ID_COLUMN),
            inverseJoinColumns = @JoinColumn(name = CATEGORY_ID_COLUMN),
            uniqueConstraints = @UniqueConstraint(columnNames = {PRODUCT_ID_COLUMN, CATEGORY_ID_COLUMN})
    )
    @Size(min = ProductConstants.MIN_CATEGORIES_IDS,
            max = ProductConstants.MAX_CATEGORIES_IDS,
            message = ProductExceptionMessage.SIZE_CATEGORIES_ID)
    private Set<CategoryEntity> categories;
}
