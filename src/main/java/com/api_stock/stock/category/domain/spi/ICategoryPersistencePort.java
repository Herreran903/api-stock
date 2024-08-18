package com.api_stock.stock.category.domain.spi;

import com.api_stock.stock.category.domain.model.Category;

public interface ICategoryPersistencePort {

    void createCategory(Category category);
}
