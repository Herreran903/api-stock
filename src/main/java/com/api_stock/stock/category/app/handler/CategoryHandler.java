package com.api_stock.stock.category.app.handler;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.dto.CategoryResponse;
import com.api_stock.stock.category.app.mapper.ICategoryRequestMapper;
import com.api_stock.stock.category.app.mapper.ICategoryResponseMapper;
import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.api.ICategoriesGetByPageServicePort;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryCreateServicePort categoryCreateService;
    private final ICategoriesGetByPageServicePort getCategoriesByPageService;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        categoryCreateService.createCategory(categoryRequestMapper.toCategory(categoryRequest));
    }

    @Override
    public CategoryPage<CategoryResponse> getCategoriesByPage(int page, int size, String sortDirection) {
        CategoryPage<Category> categories = getCategoriesByPageService.getCategoriesByPage(page, size, sortDirection);

        return categoryResponseMapper.toPageResponse(categories);
    }
}
