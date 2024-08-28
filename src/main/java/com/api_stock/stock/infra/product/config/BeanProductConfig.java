package com.api_stock.stock.infra.product.config;

import com.api_stock.stock.domain.product.api.IProductCreateServicePort;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;
import com.api_stock.stock.domain.product.usecase.ProductCreateUseCase;
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
    public IProductCreateServicePort productCreateService() {
        return new ProductCreateUseCase(productPersistence());
    }
}
