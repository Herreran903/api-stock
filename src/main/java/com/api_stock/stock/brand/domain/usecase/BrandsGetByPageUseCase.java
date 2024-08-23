package com.api_stock.stock.brand.domain.usecase;

import com.api_stock.stock.brand.domain.api.IBrandsGetByPageServicePort;
import com.api_stock.stock.brand.domain.exception.BrandExceptionMessage;
import com.api_stock.stock.brand.domain.exception.ex.BrandNotValidParameterException;
import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.model.BrandPage;
import com.api_stock.stock.brand.domain.spi.IBrandPersistencePort;
import com.api_stock.stock.brand.domain.util.BrandConstants;

public class BrandsGetByPageUseCase implements IBrandsGetByPageServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandsGetByPageUseCase(final IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public BrandPage<Brand> getBrandsByPage(int page, int size, String sortDirection) {
        if (!(BrandConstants.Sort.ASC.toString().equalsIgnoreCase(sortDirection) ||
                BrandConstants.Sort.DESC.toString().equalsIgnoreCase(sortDirection)))
            throw new BrandNotValidParameterException(BrandExceptionMessage.INVALID_SORT_DIRECTION);

        if (page < 0)
            throw new BrandNotValidParameterException(BrandExceptionMessage.NO_NEGATIVE_PAGE);


        if (size <= 0)
            throw new BrandNotValidParameterException(BrandExceptionMessage.GREATER_ZERO_SIZE);

        return brandPersistencePort.getBrandsByPage(page, size, sortDirection);
    }
}
