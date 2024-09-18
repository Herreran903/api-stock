package com.api_stock.stock.infra.brand.config;

import com.api_stock.stock.domain.brand.api.IBrandServicePort;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.brand.usecase.BrandUseCase;
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
    public IBrandServicePort brandCreateServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }
}
