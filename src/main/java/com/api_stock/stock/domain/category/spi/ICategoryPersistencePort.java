package com.api_stock.stock.domain.category.spi;

import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;

public interface ICategoryPersistencePort {

    void createCategory(Category category);
    PageData<Category> getCategoriesByPage(int page, int size, String sortDirection);
    Boolean isCategoryPresentByName(String categoryName);
}
