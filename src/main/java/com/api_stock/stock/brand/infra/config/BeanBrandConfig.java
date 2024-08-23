package com.api_stock.stock.brand.infra.config;

import com.api_stock.stock.brand.domain.api.IBrandCreateServicePort;
import com.api_stock.stock.brand.domain.spi.IBrandPersistencePort;
import com.api_stock.stock.brand.domain.usecase.BrandCreateUseCase;
import com.api_stock.stock.brand.infra.out.BrandAdapter;
import com.api_stock.stock.brand.infra.out.IBrandMapper;
import com.api_stock.stock.brand.infra.out.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanBrandConfig {

    private final IBrandRepository brandRepository;
    private final IBrandMapper brandMapper;

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandAdapter(brandRepository, brandMapper);
    }

    @Bean
    public IBrandCreateServicePort brandCreateServicePort() {
        return new BrandCreateUseCase(brandPersistencePort());
    }
}
