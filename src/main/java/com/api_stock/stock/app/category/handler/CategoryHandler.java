package com.api_stock.stock.app.category.handler;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.app.category.dto.CategoryResponse;
import com.api_stock.stock.app.category.mapper.ICategoryRequestMapper;
import com.api_stock.stock.app.category.mapper.ICategoryResponseMapper;
import com.api_stock.stock.domain.category.api.ICategoryCreateServicePort;
import com.api_stock.stock.domain.category.api.ICategoriesGetByPageServicePort;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
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
    public void createBrand(CategoryRequest categoryRequest) {
        categoryCreateService.createCategory(categoryRequestMapper.toCategory(categoryRequest));
    }

    @Override
    public PageData<CategoryResponse> getCategoriesByPage(int page, int size, String sortDirection) {
        PageData<Category> categories = getCategoriesByPageService.getCategoriesByPage(page, size, sortDirection);

        return categoryResponseMapper.toPageResponse(categories);
    }
}
