package com.api_stock.stock.category.infra.config;

import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.api.ICategoriesGetByPageServicePort;
import com.api_stock.stock.category.domain.spi.IBrandPersistencePort;
import com.api_stock.stock.category.domain.usecase.BrandCreateUseCase;
import com.api_stock.stock.category.domain.usecase.CategoriesGetByPageUseCase;
import com.api_stock.stock.category.infra.out.CategoryAdapter;
import com.api_stock.stock.category.infra.out.ICategoryMapper;
import com.api_stock.stock.category.infra.out.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanCategoryConfig {

    private final ICategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;

    @Bean
    public IBrandPersistencePort categoryPersistence() {
        return new CategoryAdapter(categoryRepository, categoryMapper);
    }

    @Bean
    public ICategoryCreateServicePort categoryCreateService() {
        return new BrandCreateUseCase(categoryPersistence());
    }

    @Bean
    ICategoriesGetByPageServicePort getCategoriesByPageService() {
        return new CategoriesGetByPageUseCase(categoryPersistence());
    }
}
