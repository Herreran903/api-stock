package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.api.ICategoriesGetByPageServicePort;
import com.api_stock.stock.category.domain.exception.CategoryExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.category.domain.model.Brand;
import com.api_stock.stock.category.domain.model.CategoryPage;
import com.api_stock.stock.category.domain.spi.IBrandPersistencePort;
import com.api_stock.stock.category.domain.util.CategoryConstants;

public class CategoriesGetByPageUseCase implements ICategoriesGetByPageServicePort {

    private final IBrandPersistencePort categoryPersistencePort;

    public CategoriesGetByPageUseCase(IBrandPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public CategoryPage<Brand> getCategoriesByPage(int page, int size, String sortDirection) {

        if (!(CategoryConstants.Sort.ASC.toString().equalsIgnoreCase(sortDirection) ||
                CategoryConstants.Sort.DESC.toString().equalsIgnoreCase(sortDirection)))
            throw new CategoryNotValidParameterException(CategoryExceptionMessage.INVALID_SORT_DIRECTION);

        if (page < 0)
            throw new CategoryNotValidParameterException(CategoryExceptionMessage.NO_NEGATIVE_PAGE);


        if (size <= 0)
            throw new CategoryNotValidParameterException(CategoryExceptionMessage.GREATER_ZERO_SIZE);

        return categoryPersistencePort.getCategoriesByPage(page, size, sortDirection);
    }
}
