package com.api_stock.stock.category.domain.spi;

import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;

public interface ICategoryPersistencePort {

    void createCategory(Category category);
    CategoryPage<Category> getCategoriesByPage(int page, int size, String sortDirection);
    Boolean isCategoryPresentByName(String categoryName);
}
