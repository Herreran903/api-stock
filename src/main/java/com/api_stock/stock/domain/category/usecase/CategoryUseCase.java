package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.api.ICategoryServicePort;
import com.api_stock.stock.domain.category.exception.ex.CategoriesNotFoundByIdsException;
import com.api_stock.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
import com.api_stock.stock.domain.page.PageData;

import java.util.List;

import static com.api_stock.stock.domain.category.exception.CategoryExceptionMessage.*;
import static com.api_stock.stock.domain.category.util.CategoryConstants.MAX_DESCRIPTION_LENGTH;
import static com.api_stock.stock.domain.category.util.CategoryConstants.MAX_NAME_LENGTH;
import static com.api_stock.stock.domain.util.GlobalConstants.*;
import static com.api_stock.stock.domain.util.GlobalExceptionMessage.*;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createCategory(Category category) {

        String categoryName = category.getName();
        String categoryDescription = category.getDescription();

        if (categoryName == null || categoryName.trim().isEmpty())
            throw new CategoryNotValidFieldException(EMPTY_NAME);

        if (categoryDescription == null || categoryDescription.trim().isEmpty())
            throw new CategoryNotValidFieldException(EMPTY_DESCRIPTION);

        if (categoryName.length() > MAX_NAME_LENGTH)
            throw new CategoryNotValidFieldException(TOO_LONG_NAME);

        if (categoryDescription.length() > MAX_DESCRIPTION_LENGTH)
            throw new CategoryNotValidFieldException(TOO_LONG_DESCRIPTION);

        if (Boolean.TRUE.equals(categoryPersistencePort.isCategoryPresentByName(categoryName)))
            throw new CategoryAlreadyExistException(ALREADY_EXIST_CATEGORY);

        categoryPersistencePort.createCategory(category);
    }


    @Override
    public List<Category> getCategoriesByIds(List<Long> ids) {

        List<Category> categories = categoryPersistencePort.getCategoriesByIds(ids);

        if (categories.size() != ids.size()) {
            List<Long> foundIds = categories.stream().map(Category::getId).toList();
            List<Long> missingIds = ids.stream().filter(id -> !foundIds.contains(id)).toList();

            throw new CategoriesNotFoundByIdsException(NO_FOUND_CATEGORIES, missingIds);
        }

        return categories;
    }


    @Override
    public PageData<Category> getCategoriesByPage(int page, int size, String order) {

        if (!(ASC.equalsIgnoreCase(order) || DESC.equalsIgnoreCase(order)))
            throw new CategoryNotValidParameterException(INVALID_ORDER);

        if (page < MIN_PAGE_NUMBER)
            throw new CategoryNotValidParameterException(NO_NEGATIVE_PAGE);

        if (size < MIN_PAGE_SIZE)
            throw new CategoryNotValidParameterException(GREATER_ZERO_SIZE);

        return categoryPersistencePort.getCategoriesByPage(page, size, order);
    }
}
