package com.api_stock.stock.infra.product.config;

import com.api_stock.stock.domain.product.api.IProductServicePort;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;
import com.api_stock.stock.domain.product.usecase.ProductUseCase;
import com.api_stock.stock.infra.product.out.IProductMapper;
import com.api_stock.stock.infra.product.out.IProductRepository;
import com.api_stock.stock.infra.product.out.ProductAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanProductConfig {
    private final IProductRepository productRepository;
    private final IProductMapper productMapper;

    @Bean
    public IProductPersistencePort productPersistence() {
        return new ProductAdapter(productRepository, productMapper);
    }

    @Bean
    public IProductServicePort productCreateService() {
        return new ProductUseCase(productPersistence());
    }
}
