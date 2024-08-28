package com.api_stock.stock.infra.category.out;

import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class CategoryAdapter implements ICategoryPersistencePort {

    ICategoryRepository categoryRepository;
    ICategoryMapper categoryMapper;

    public CategoryAdapter(ICategoryRepository categoryRepository, ICategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(categoryMapper.toEntity(category));
    }

    @Override
    public Boolean isCategoryPresentByName(String name){
        return categoryRepository.findByName(name).isPresent();
    }

    @Override
    public PageData<Category> getCategoriesByPage(int page, int size, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(direction, "name"));

        Page<CategoryEntity> categoryEntityPage = categoryRepository.findAll(sortedPageable);

        List<Category> categories = categoryMapper.toCategoriesList(categoryEntityPage.getContent());

        return new PageData<>(
                categories,
                categoryEntityPage.getNumber(),
                (int) categoryEntityPage.getTotalElements(),
                categoryEntityPage.isFirst(),
                categoryEntityPage.isLast(),
                categoryEntityPage.hasNext(),
                categoryEntityPage.hasPrevious()
        );
    }

    @Override
    public List<Category> getCategoriesByIds(List<Long> ids) {
        return categoryMapper.toCategoriesList(categoryRepository.findAllById(ids));
    }
}
