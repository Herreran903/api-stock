package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.category.domain.exception.ExceptionMessage;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import com.api_stock.stock.category.domain.util.Constants;

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
            throw new CategoryNotValidFieldException(ExceptionMessage.EMPTY_NAME);

        if (categoryDescription == null || categoryDescription.trim().isEmpty())
            throw new CategoryNotValidFieldException(ExceptionMessage.EMPTY_DESCRIPTION);

        if (categoryName.length() > Constants.MAX_NAME_LENGTH)
            throw new CategoryNotValidFieldException(ExceptionMessage.TOO_LONG_NAME);

        if (categoryDescription.length() > Constants.MAX_DESCRIPTION_LENGTH)
            throw new CategoryNotValidFieldException(ExceptionMessage.TOO_LONG_DESCRIPTION);

        if (Boolean.TRUE.equals(categoryPersistencePort.isCategoryPresentByName(categoryName)))
            throw new CategoryAlreadyExistException(ExceptionMessage.ALREADY_EXIST_CATEGORY);

        categoryPersistencePort.createCategory(category);
    }
}
