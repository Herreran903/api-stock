package com.api_stock.stock.category.infra.out;

import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class CategoryAdapter implements ICategoryPersistencePort {

    ICategoryRepository repository;
    ICategoryMapper mapper;

    public CategoryAdapter(ICategoryRepository repository, ICategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void createCategory(Category category) {
        repository.save(mapper.toEntity(category));
    }

    @Override
    public Boolean isCategoryPresentByName(String name){
        return repository.findByName(name).isPresent();
    }

    @Override
    public CategoryPage<Category> getCategoriesByPage(int page, int size, String orderDirection) {
        Sort.Direction direction = Sort.Direction.fromString(orderDirection);
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(direction, "name"));

        Page<CategoryEntity> categoryEntityPage = repository.findAll(sortedPageable);

        List<Category> categories = mapper.toCategoriesList(categoryEntityPage.getContent());

        return new CategoryPage<>(
                categories,
                categoryEntityPage.getNumber(),
                (int) categoryEntityPage.getTotalElements(),
                categoryEntityPage.isFirst(),
                categoryEntityPage.isLast(),
                categoryEntityPage.hasNext(),
                categoryEntityPage.hasPrevious()
        );
    }


}
