package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.infra.brand.out.BrandEntity;
import com.api_stock.stock.infra.category.out.CategoryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal price;

    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id",nullable = false)
    private BrandEntity brand;

    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryEntity> categories;
}
