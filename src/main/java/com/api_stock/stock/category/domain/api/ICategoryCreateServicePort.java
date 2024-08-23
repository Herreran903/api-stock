package com.api_stock.stock.category.domain.api;

import com.api_stock.stock.category.domain.model.Brand;

public interface ICategoryCreateServicePort {

    void createCategory(Brand brand);
}
