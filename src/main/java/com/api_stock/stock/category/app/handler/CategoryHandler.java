package com.api_stock.stock.category.app.handler;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.dto.CategoryResponse;
import com.api_stock.stock.category.app.exception.ExceptionMessage;
import com.api_stock.stock.category.app.exception.ex.InvalidParameterException;
import com.api_stock.stock.category.app.mapper.ICategoryRequestMapper;
import com.api_stock.stock.category.app.mapper.ICategoryResponseMapper;
import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.api.IGetCategoriesByPageServicePort;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;
import com.api_stock.stock.category.domain.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryHandler implements ICategoryHandler {

    private final ICategoryCreateServicePort categoryCreateService;
    private final IGetCategoriesByPageServicePort getCategoriesByPageService;
    private final ICategoryRequestMapper categoryRequestMapper;
    private final ICategoryResponseMapper categoryResponseMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        categoryCreateService.createCategory(categoryRequestMapper.toCategory(categoryRequest));
    }

    @Override
    public CategoryPage<CategoryResponse> getCategoriesByPage(int page, int size, String sortDirection) {

        if (!(Constants.Sort.ASC.toString().equalsIgnoreCase(sortDirection) ||
                Constants.Sort.DESC.toString().equalsIgnoreCase(sortDirection))) {
            throw new InvalidParameterException(ExceptionMessage.INVALID_SORT_DIRECTION.getMessage());
        }

        if (page < 0){
            throw new InvalidParameterException(ExceptionMessage.NO_NEGATIVE_PAGE.getMessage());
        }

        if (size <= 0){
            throw new InvalidParameterException(ExceptionMessage.SIZE_GREATER_ZERO.getMessage());
        }

        CategoryPage<Category> categories = getCategoriesByPageService.getCategoriesByPage(page, size, sortDirection);

        return categoryResponseMapper.toPageResponse(categories);
    }
}
