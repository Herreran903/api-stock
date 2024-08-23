package com.api_stock.stock.brand.domain.api;

import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.model.BrandPage;

public interface IBrandsGetByPageServicePort {
    BrandPage<Brand> getBrandsByPage(int page, int size, String sortDirection);
}
