package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.category.domain.exception.CategoryExceptionMessage;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import com.api_stock.stock.category.domain.util.CategoryConstants;

public class CategoryCreateUseCase implements ICategoryCreateServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryCreateUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Category category) {

        String categoryName = category.getName();
        String categoryDescription = category.getDescription();

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

        categoryPersistencePort.createCategory(category);
    }
}
