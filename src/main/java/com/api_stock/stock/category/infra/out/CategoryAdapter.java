package com.api_stock.stock.category.infra.out;

import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import com.api_stock.stock.category.infra.exception.ex.CategoryAlreadyExistException;

public class CategoryAdapter implements ICategoryPersistencePort {

    ICategoryRepository repository;
    ICategoryMapper mapper;

    public CategoryAdapter(ICategoryRepository repository, ICategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void createCategory(Category category) {
        if (repository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistException();
        }

        repository.save(mapper.toEntity(category));
    }
}
