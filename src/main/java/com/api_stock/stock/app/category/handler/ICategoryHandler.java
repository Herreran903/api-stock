package com.api_stock.stock.app.category.handler;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.app.category.dto.CategoryResponse;
import com.api_stock.stock.domain.page.PageData;

public interface ICategoryHandler {
    void createBrand(CategoryRequest categoryRequest);
    PageData<CategoryResponse> getCategoriesByPage(int page, int size, String order);
}
