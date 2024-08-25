package com.api_stock.stock.app.brand.dto;

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
