package com.api_stock.stock.brand.domain.spi;

import com.api_stock.stock.brand.domain.model.Brand;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    Boolean isBrandPresentByName(String brandName);
}
