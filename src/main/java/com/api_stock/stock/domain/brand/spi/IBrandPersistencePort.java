package com.api_stock.stock.domain.brand.spi;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.page.PageData;

import java.util.Optional;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    PageData<Brand> getBrandsByPage(int page, int size, String order);
    Boolean isBrandPresentByName(String brandName);
    Optional<Brand> getBrandById(Long brandId);
}
