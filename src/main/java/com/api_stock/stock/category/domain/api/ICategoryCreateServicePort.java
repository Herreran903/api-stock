package com.api_stock.stock.category.domain.api;

import com.api_stock.stock.category.domain.model.Category;

public interface ICategoryCreateServicePort {

    void createCategory(Category category);
}
