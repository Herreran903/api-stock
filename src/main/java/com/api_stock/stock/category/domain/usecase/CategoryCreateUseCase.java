package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.exception.ex.MaxLengthExceededException;
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
        if (category.getName().length() > Constants.MAX_NAME_LENGTH) {
            throw new MaxLengthExceededException(
                    Constants.Field.NAME.toString(), Constants.MAX_NAME_LENGTH);
        }

        if (category.getDescription().length() > Constants.MAX_DESCRIPTION_LENGTH) {
            throw new MaxLengthExceededException(
                    Constants.Field.DESCRIPTION.toString(), Constants.MAX_DESCRIPTION_LENGTH);
        }

        categoryPersistencePort.createCategory(category);
    }
}
