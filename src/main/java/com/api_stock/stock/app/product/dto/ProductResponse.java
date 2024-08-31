package com.api_stock.stock.app.product.dto;

import com.api_stock.stock.app.brand.dto.BrandResponse;
import com.api_stock.stock.app.category.dto.CategoryProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private BrandResponse brand;
    private List<CategoryProductResponse> categories;
}
