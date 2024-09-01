package com.api_stock.stock.infra.category.config;

import com.api_stock.stock.domain.category.api.ICategoryServicePort;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
import com.api_stock.stock.domain.category.usecase.CategoryUseCase;
import com.api_stock.stock.infra.category.out.CategoryAdapter;
import com.api_stock.stock.infra.category.out.ICategoryMapper;
import com.api_stock.stock.infra.category.out.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanCategoryConfig {

    private final ICategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistence() {
        return new CategoryAdapter(categoryRepository, categoryMapper);
    }

    @Bean
    public ICategoryServicePort categoryCreateService() {
        return new CategoryUseCase(categoryPersistence());
    }
}
