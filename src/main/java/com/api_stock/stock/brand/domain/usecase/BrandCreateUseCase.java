package com.api_stock.stock.brand.domain.usecase;

import com.api_stock.stock.brand.domain.api.IBrandCreateServicePort;
import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.spi.IBrandPersistencePort;

public class BrandCreateUseCase implements IBrandCreateServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandCreateUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {

        brandPersistencePort.createBrand(brand);
    }
}
