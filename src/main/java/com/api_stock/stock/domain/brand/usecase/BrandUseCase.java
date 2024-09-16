package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.api.IBrandServicePort;
import com.api_stock.stock.domain.brand.exception.ex.BrandAlreadyExistException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidFieldException;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidParameterException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;

import static com.api_stock.stock.domain.brand.exception.BrandExceptionMessage.*;
import static com.api_stock.stock.domain.brand.util.BrandConstants.MAX_DESCRIPTION_LENGTH;
import static com.api_stock.stock.domain.brand.util.BrandConstants.MAX_NAME_LENGTH;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void createBrand(Brand brand) {

        String brandName = brand.getName();
        String brandDescription = brand.getDescription();

        if (brandName == null || brandName.trim().isEmpty())
            throw new BrandNotValidFieldException(EMPTY_NAME);

        if (brandDescription == null || brandDescription.trim().isEmpty())
            throw new BrandNotValidFieldException(EMPTY_DESCRIPTION);

        if (brandName.length() > MAX_NAME_LENGTH)
            throw new BrandNotValidFieldException(TOO_LONG_NAME);

        if (brandDescription.length() > MAX_DESCRIPTION_LENGTH)
            throw new BrandNotValidFieldException(TOO_LONG_DESCRIPTION);

        if (Boolean.TRUE.equals(brandPersistencePort.isBrandPresentByName(brandName)))
            throw new BrandAlreadyExistException(ALREADY_EXIST_BRAND);

        brandPersistencePort.createBrand(brand);
    }

    @Override
    public PageData<Brand> getBrandsByPage(int page, int size, String sortDirection) {
        if (!(GlobalConstants.DESC.equalsIgnoreCase(sortDirection) || GlobalConstants.ASC.equalsIgnoreCase(sortDirection)))
            throw new BrandNotValidParameterException(GlobalExceptionMessage.INVALID_SORT_DIRECTION);

        if (page < 0)
            throw new BrandNotValidParameterException(GlobalExceptionMessage.NO_NEGATIVE_PAGE);

        if (size <= 0)
            throw new BrandNotValidParameterException(GlobalExceptionMessage.GREATER_ZERO_SIZE);

        return brandPersistencePort.getBrandsByPage(page, size, sortDirection);
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandPersistencePort.getBrandById(id)
                .orElseThrow(() -> new BrandNotFoundByIdException(NO_FOUND_BRAND));
    }
}
