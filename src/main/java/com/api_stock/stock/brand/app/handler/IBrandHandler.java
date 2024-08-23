package com.api_stock.stock.brand.app.handler;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.app.dto.BrandResponse;
import com.api_stock.stock.brand.domain.model.BrandPage;

public interface IBrandHandler {
    void createBrand(BrandRequest brandRequest);
    BrandPage<BrandResponse> getBrandsByPage(int page, int size, String sortDirection);
}
