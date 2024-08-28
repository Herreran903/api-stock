package com.api_stock.stock.domain.category.api;

import com.api_stock.stock.domain.category.model.Category;

import java.util.List;

public interface ICategoriesGetByIdsServicePort {
    List<Category> getCategoriesByIds(List<Long> ids);
}
