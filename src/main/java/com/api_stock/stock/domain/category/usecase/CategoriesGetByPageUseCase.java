package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.api.ICategoriesGetByPageServicePort;
import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;

public class CategoriesGetByPageUseCase implements ICategoriesGetByPageServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoriesGetByPageUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public PageData<Category> getCategoriesByPage(int page, int size, String sortDirection) {

        if (!(GlobalConstants.ASC.equalsIgnoreCase(sortDirection) || GlobalConstants.DESC.equalsIgnoreCase(sortDirection)))
            throw new CategoryNotValidParameterException(CategoryExceptionMessage.INVALID_SORT_DIRECTION);

        if (page < 0)
            throw new CategoryNotValidParameterException(CategoryExceptionMessage.NO_NEGATIVE_PAGE);

        if (size <= 0)
            throw new CategoryNotValidParameterException(CategoryExceptionMessage.GREATER_ZERO_SIZE);

        return categoryPersistencePort.getCategoriesByPage(page, size, sortDirection);
    }
}
