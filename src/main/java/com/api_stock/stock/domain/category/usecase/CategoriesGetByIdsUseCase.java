package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.api.ICategoriesGetByIdsServicePort;
import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoriesNotFoundByIdsException;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;

import java.util.List;

public class CategoriesGetByIdsUseCase implements ICategoriesGetByIdsServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoriesGetByIdsUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
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
}
