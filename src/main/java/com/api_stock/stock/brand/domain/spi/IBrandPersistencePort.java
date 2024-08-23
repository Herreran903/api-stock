package com.api_stock.stock.brand.domain.spi;

import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.model.BrandPage;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    BrandPage<Brand> getBrandsByPage(int page, int size, String sortDirection);
    Boolean isBrandPresentByName(String brandName);
}
