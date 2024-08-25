package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.api.IBrandCreateServicePort;
import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.brand.util.BrandConstants;

public class BrandCreateUseCase implements IBrandCreateServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandCreateUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {

        String brandName = brand.getName();
        String brandDescription = brand.getDescription();

        if (brandName == null || brandName.trim().isEmpty())
            throw new BrandNotValidFieldException(BrandExceptionMessage.EMPTY_NAME);

        if (brandDescription == null || brandDescription.trim().isEmpty())
            throw new BrandNotValidFieldException(BrandExceptionMessage.EMPTY_DESCRIPTION);

        if (brandName.length() > BrandConstants.MAX_NAME_LENGTH)
            throw new BrandNotValidFieldException(BrandExceptionMessage.TOO_LONG_NAME);

        if (brandDescription.length() > BrandConstants.MAX_DESCRIPTION_LENGTH)
            throw new BrandNotValidFieldException(BrandExceptionMessage.TOO_LONG_DESCRIPTION);

        if (Boolean.TRUE.equals(brandPersistencePort.isBrandPresentByName(brandName)))
            throw new BrandAlreadyExistException(BrandExceptionMessage.ALREADY_EXIST_BRAND);

        brandPersistencePort.createBrand(brand);
    }
}
