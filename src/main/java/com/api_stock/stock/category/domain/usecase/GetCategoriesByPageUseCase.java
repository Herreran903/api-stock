package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.api.IGetCategoriesByPageServicePort;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;

public class GetCategoriesByPageUseCase implements IGetCategoriesByPageServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public GetCategoriesByPageUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public CategoryPage<Category> getCategoriesByPage(int page, int size, String sortDirection) {
        return categoryPersistencePort.getCategoriesByPage(page, size, sortDirection);
    }
}
