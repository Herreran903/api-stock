package com.api_stock.stock.category.domain.spi;

import com.api_stock.stock.category.domain.model.Brand;
import com.api_stock.stock.category.domain.model.CategoryPage;

public interface IBrandPersistencePort {

    void createCategory(Brand brand);
    CategoryPage<Brand> getCategoriesByPage(int page, int size, String sortDirection);
    Boolean isBradPresentByName(String categoryName);
}
