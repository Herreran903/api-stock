package com.api_stock.stock.domain.product.model;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.model.Category;

import java.math.BigDecimal;
import java.util.List;

public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    private Brand brand;
    private List<Category> categories;

    public Product(Long id, String name, String description, BigDecimal price, Integer stock, Brand brand, List<Category> categories) {
        this.categories = categories;
        this.stock = stock;
        this.name = name;
        this.id = id;
        this.description = description;
        this.price = price;
        this.brand = brand;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
