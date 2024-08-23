package com.api_stock.stock.category.domain.api;

import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;

public interface ICategoriesGetByPageServicePort {
    CategoryPage<Category> getCategoriesByPage(int page, int size, String sortDirection);
}
