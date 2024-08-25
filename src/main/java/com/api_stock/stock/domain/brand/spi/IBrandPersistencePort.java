package com.api_stock.stock.domain.brand.spi;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.page.PageData;

public interface IBrandPersistencePort {
    void createBrand(Brand brand);
    PageData<Brand> getBrandsByPage(int page, int size, String sortDirection);
    Boolean isBrandPresentByName(String brandName);
}
