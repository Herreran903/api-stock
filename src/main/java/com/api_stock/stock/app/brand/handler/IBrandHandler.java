package com.api_stock.stock.app.brand.handler;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.app.brand.dto.BrandResponse;
import com.api_stock.stock.domain.page.PageData;

public interface IBrandHandler {
    void createBrand(BrandRequest brandRequest);
    PageData<BrandResponse> getBrandsByPage(int page, int size, String order);
}
