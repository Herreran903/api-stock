package com.api_stock.stock.domain.category.api;

import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;

import java.util.List;

public interface ICategoryServicePort {
    void createCategory(Category category);
    PageData<Category> getCategoriesByPage(int page, int size, String order);
    List<Category> getCategoriesByIds(List<Long> ids);
}
