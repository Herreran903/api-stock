package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.api.IBrandsGetByPageServicePort;
import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidParameterException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;

public class BrandsGetByPageUseCase implements IBrandsGetByPageServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandsGetByPageUseCase(final IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public PageData<Brand> getBrandsByPage(int page, int size, String sortDirection) {
        if (!(GlobalConstants.DESC.equalsIgnoreCase(sortDirection) || GlobalConstants.ASC.equalsIgnoreCase(sortDirection)))
            throw new BrandNotValidParameterException(BrandExceptionMessage.INVALID_SORT_DIRECTION);

        if (page < 0)
            throw new BrandNotValidParameterException(BrandExceptionMessage.NO_NEGATIVE_PAGE);

        if (size <= 0)
            throw new BrandNotValidParameterException(BrandExceptionMessage.GREATER_ZERO_SIZE);

        return brandPersistencePort.getBrandsByPage(page, size, sortDirection);
    }
}
