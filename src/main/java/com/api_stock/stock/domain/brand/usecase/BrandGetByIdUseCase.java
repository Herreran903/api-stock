package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.api.IBrandGetByIdServicePort;
import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;

public class BrandGetByIdUseCase implements IBrandGetByIdServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandGetByIdUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandPersistencePort.getBrandById(id)
                .orElseThrow(() -> new BrandNotFoundByIdException(BrandExceptionMessage.NO_FOUND_BRAND));
    }
}
