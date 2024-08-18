package com.api_stock.stock.category.config;

import com.api_stock.stock.category.app.handler.CategoryHandler;
import com.api_stock.stock.category.app.handler.ICategoryHandler;
import com.api_stock.stock.category.app.mapper.ICategoryRequestMapper;
import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import com.api_stock.stock.category.domain.usecase.CategoryCreateUseCase;
import com.api_stock.stock.category.infra.in.CategoryController;
import com.api_stock.stock.category.infra.out.CategoryAdapter;
import com.api_stock.stock.category.infra.out.ICategoryMapper;
import com.api_stock.stock.category.infra.out.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final ICategoryRepository categoryRepository;
    private final ICategoryMapper categoryMapper;
    private final ICategoryRequestMapper categoryRequestMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistence() {
        return new CategoryAdapter(categoryRepository, categoryMapper);
    }

    @Bean
    public ICategoryCreateServicePort categoryCreateService() {
        return new CategoryCreateUseCase(categoryPersistence());
    }

    @Bean
    public ICategoryHandler categoryHandler() {
        return new CategoryHandler(categoryCreateService(),
                categoryRequestMapper);
    }

    @Bean
    public CategoryController categoryController() {
        return new CategoryController(categoryHandler());
    }
}
