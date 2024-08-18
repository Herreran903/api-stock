package com.api_stock.stock.category.app.handler;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.mapper.ICategoryRequestMapper;
import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;

public class CategoryHandler implements ICategoryHandler {

    private final ICategoryCreateServicePort categoryCreateService;
    private final ICategoryRequestMapper categoryRequestMapper;

    public CategoryHandler(ICategoryCreateServicePort categoryCreateService,
                           ICategoryRequestMapper categoryRequestMapper) {
        this.categoryCreateService = categoryCreateService;
        this.categoryRequestMapper = categoryRequestMapper;
    }

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        categoryCreateService.createCategory(categoryRequestMapper.toCategory(categoryRequest));
    }
}
