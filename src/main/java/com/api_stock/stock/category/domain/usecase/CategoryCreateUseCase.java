package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.exception.ExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.EmptyFieldException;
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

        String categoryName = category.getName();
        String categoryDescription = category.getDescription();

        if (categoryName == null || categoryName.trim().isEmpty())
            throw new EmptyFieldException(ExceptionMessage.FIELD_EMPTY.getMessage(
                            Constants.Field.NAME.toString(), 0
            ));

        if (categoryDescription == null || categoryDescription.trim().isEmpty())
            throw new EmptyFieldException(ExceptionMessage.FIELD_EMPTY.getMessage(
                            Constants.Field.DESCRIPTION.toString(), 0
            ));

        if (categoryName.length() > Constants.MAX_NAME_LENGTH) {
            throw new MaxLengthExceededException(ExceptionMessage.FIELD_TOO_LONG.getMessage(
                            Constants.Field.NAME.toString(), Constants.MAX_NAME_LENGTH
            ));
        }

        if (categoryDescription.length() > Constants.MAX_DESCRIPTION_LENGTH) {
            throw new MaxLengthExceededException(ExceptionMessage.FIELD_TOO_LONG.getMessage(
                    Constants.Field.DESCRIPTION.toString(), Constants.MAX_DESCRIPTION_LENGTH
            ));
        }

        categoryPersistencePort.createCategory(category);
    }
}
