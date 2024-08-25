package com.api_stock.stock.domain.category.api;

import com.api_stock.stock.domain.category.model.Category;

public interface ICategoryCreateServicePort {

    void createCategory(Category category);
}
