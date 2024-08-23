package com.api_stock.stock.category.domain.api;

import com.api_stock.stock.category.domain.model.Brand;
import com.api_stock.stock.category.domain.model.CategoryPage;

public interface ICategoriesGetByPageServicePort {
    CategoryPage<Brand> getCategoriesByPage(int page, int size, String sortDirection);
}
