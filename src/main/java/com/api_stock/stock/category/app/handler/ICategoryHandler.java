package com.api_stock.stock.category.app.handler;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.dto.CategoryResponse;
import com.api_stock.stock.category.domain.model.CategoryPage;

public interface ICategoryHandler {

    void createBrand(CategoryRequest categoryRequest);
    CategoryPage<CategoryResponse> getCategoriesByPage(int page, int size, String sortDirection);
}
