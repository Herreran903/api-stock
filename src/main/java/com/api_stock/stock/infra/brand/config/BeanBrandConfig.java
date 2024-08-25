package com.api_stock.stock.infra.brand.config;

import com.api_stock.stock.domain.brand.api.IBrandCreateServicePort;
import com.api_stock.stock.domain.brand.api.IBrandsGetByPageServicePort;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.brand.usecase.BrandCreateUseCase;
import com.api_stock.stock.domain.brand.usecase.BrandsGetByPageUseCase;
import com.api_stock.stock.infra.brand.out.BrandAdapter;
import com.api_stock.stock.infra.brand.out.IBrandMapper;
import com.api_stock.stock.infra.brand.out.IBrandRepository;
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

    @Bean
    public IBrandsGetByPageServicePort brandsGetByPageServicePort() {
        return new BrandsGetByPageUseCase(brandPersistencePort());
    }
}
