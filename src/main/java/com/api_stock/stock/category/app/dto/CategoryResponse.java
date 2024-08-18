package com.api_stock.stock.category.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;

    public CategoryResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}
