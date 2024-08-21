package com.api_stock.stock.brand.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BrandResponse {
    private Long id;
    private String name;
    private String description;
}
