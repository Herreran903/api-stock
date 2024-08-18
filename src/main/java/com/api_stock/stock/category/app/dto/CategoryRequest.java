package com.api_stock.stock.category.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequest {

    private String name;
    private String description;

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
