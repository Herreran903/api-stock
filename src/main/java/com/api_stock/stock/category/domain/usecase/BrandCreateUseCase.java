package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.category.domain.exception.CategoryExceptionMessage;
import com.api_stock.stock.category.domain.model.Brand;
import com.api_stock.stock.category.domain.spi.IBrandPersistencePort;
import com.api_stock.stock.category.domain.util.CategoryConstants;

public class BrandCreateUseCase implements ICategoryCreateServicePort {

    private final IBrandPersistencePort categoryPersistencePort;

    public BrandCreateUseCase(IBrandPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Brand brand) {

        String categoryName = brand.getName();
        String categoryDescription = brand.getDescription();

        if (categoryName == null || categoryName.trim().isEmpty())
            throw new CategoryNotValidFieldException(CategoryExceptionMessage.EMPTY_NAME);

        if (categoryDescription == null || categoryDescription.trim().isEmpty())
            throw new CategoryNotValidFieldException(CategoryExceptionMessage.EMPTY_DESCRIPTION);

        if (categoryName.length() > CategoryConstants.MAX_NAME_LENGTH)
            throw new CategoryNotValidFieldException(CategoryExceptionMessage.TOO_LONG_NAME);

        if (categoryDescription.length() > CategoryConstants.MAX_DESCRIPTION_LENGTH)
            throw new CategoryNotValidFieldException(CategoryExceptionMessage.TOO_LONG_DESCRIPTION);

        if (Boolean.TRUE.equals(categoryPersistencePort.isBradPresentByName(categoryName)))
            throw new CategoryAlreadyExistException(CategoryExceptionMessage.ALREADY_EXIST_CATEGORY);

        categoryPersistencePort.createCategory(brand);
    }
}
