package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.api.ICategoryServicePort;
import com.api_stock.stock.domain.category.exception.ex.CategoriesNotFoundByIdsException;
import com.api_stock.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
import com.api_stock.stock.domain.category.util.CategoryConstants;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;

import java.util.List;

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
            throw new CategoryNotValidFieldException(CategoryExceptionMessage.EMPTY_NAME);

        if (categoryDescription == null || categoryDescription.trim().isEmpty())
            throw new CategoryNotValidFieldException(CategoryExceptionMessage.EMPTY_DESCRIPTION);

        if (categoryName.length() > CategoryConstants.MAX_NAME_LENGTH)
            throw new CategoryNotValidFieldException(CategoryExceptionMessage.TOO_LONG_NAME);

        if (categoryDescription.length() > CategoryConstants.MAX_DESCRIPTION_LENGTH)
            throw new CategoryNotValidFieldException(CategoryExceptionMessage.TOO_LONG_DESCRIPTION);

        if (Boolean.TRUE.equals(categoryPersistencePort.isCategoryPresentByName(categoryName)))
            throw new CategoryAlreadyExistException(CategoryExceptionMessage.ALREADY_EXIST_CATEGORY);

        categoryPersistencePort.createCategory(category);
    }


    @Override
    public List<Category> getCategoriesByIds(List<Long> ids) {

        List<Category> categories = categoryPersistencePort.getCategoriesByIds(ids);

        if (categories.size() != ids.size()) {
            List<Long> foundIds = categories.stream().map(Category::getId).toList();
            List<Long> missingIds = ids.stream().filter(id -> !foundIds.contains(id)).toList();

            throw new CategoriesNotFoundByIdsException(CategoryExceptionMessage.NO_FOUND_CATEGORIES, missingIds);
        }

        return categories;
    }


    @Override
    public PageData<Category> getCategoriesByPage(int page, int size, String sortDirection) {

        if (!(GlobalConstants.ASC.equalsIgnoreCase(sortDirection) || GlobalConstants.DESC.equalsIgnoreCase(sortDirection)))
            throw new CategoryNotValidParameterException(GlobalExceptionMessage.INVALID_SORT_DIRECTION);

        if (page < GlobalConstants.MIN_PAGE_NUMBER)
            throw new CategoryNotValidParameterException(GlobalExceptionMessage.NO_NEGATIVE_PAGE);

        if (size < GlobalConstants.MIN_PAGE_SIZE)
            throw new CategoryNotValidParameterException(GlobalExceptionMessage.GREATER_ZERO_SIZE);

        return categoryPersistencePort.getCategoriesByPage(page, size, sortDirection);
    }
}
