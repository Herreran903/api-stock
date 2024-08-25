package com.api_stock.stock.domain.brand.api;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.page.PageData;

public interface IBrandsGetByPageServicePort {
    PageData<Brand> getBrandsByPage(int page, int size, String sortDirection);
}
