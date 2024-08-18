package com.api_stock.stock.category.domain.model;

import com.api_stock.stock.category.domain.exception.ex.EmptyFieldException;

public class Category {

    private Long id;
    private String name;
    private String description;

    public Category(Long id, String name, String description) {
        if (name == null || name.trim().isEmpty())
            throw new EmptyFieldException("name");

        if (description == null || description.trim().isEmpty())
            throw new EmptyFieldException("description");

        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
