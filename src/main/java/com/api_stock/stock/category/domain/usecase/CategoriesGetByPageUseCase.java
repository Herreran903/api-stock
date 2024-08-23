package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.api.ICategoriesGetByPageServicePort;
import com.api_stock.stock.category.domain.exception.ExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import com.api_stock.stock.category.domain.util.Constants;

public class CategoriesGetByPageUseCase implements ICategoriesGetByPageServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoriesGetByPageUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public CategoryPage<Category> getCategoriesByPage(int page, int size, String sortDirection) {

        if (!(Constants.Sort.ASC.toString().equalsIgnoreCase(sortDirection) ||
                Constants.Sort.DESC.toString().equalsIgnoreCase(sortDirection)))
            throw new CategoryNotValidParameterException(ExceptionMessage.INVALID_SORT_DIRECTION);

        if (page < 0)
            throw new CategoryNotValidParameterException(ExceptionMessage.NO_NEGATIVE_PAGE);


        if (size <= 0)
            throw new CategoryNotValidParameterException(ExceptionMessage.GREATER_ZERO_SIZE);

        return categoryPersistencePort.getCategoriesByPage(page, size, sortDirection);
    }
}
