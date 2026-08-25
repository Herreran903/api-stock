package com.api_stock.stock.app.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private int quantity;
}
