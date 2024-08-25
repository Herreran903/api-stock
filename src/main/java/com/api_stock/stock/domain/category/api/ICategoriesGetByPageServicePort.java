package com.api_stock.stock.domain.category.api;

import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;

public interface ICategoriesGetByPageServicePort {
    PageData<Category> getCategoriesByPage(int page, int size, String sortDirection);
}
