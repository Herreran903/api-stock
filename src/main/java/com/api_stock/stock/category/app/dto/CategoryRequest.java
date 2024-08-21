package com.api_stock.stock.category.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CategoryRequest {

    private String name;
    private String description;
}
