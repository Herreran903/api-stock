package com.api_stock.stock.brand.app.handler;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.app.dto.BrandResponse;
import com.api_stock.stock.brand.app.mapper.IBrandRequestMapper;
import com.api_stock.stock.brand.app.mapper.IBrandResponseMapper;
import com.api_stock.stock.brand.domain.api.IBrandCreateServicePort;
import com.api_stock.stock.brand.domain.api.IBrandsGetByPageServicePort;
import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.model.BrandPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandHandler implements IBrandHandler {

    private final IBrandCreateServicePort brandCreateServicePort;
    private final IBrandsGetByPageServicePort brandsGetByPageServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @Override
    public void createBrand(BrandRequest brandRequest) {
        brandCreateServicePort.createBrand(brandRequestMapper.toBrand(brandRequest));
    }

    @Override
    public BrandPage<BrandResponse> getBrandsByPage(int page, int size, String sortDirection) {
        BrandPage<Brand> brands = brandsGetByPageServicePort.getBrandsByPage(page, size, sortDirection);

        return brandResponseMapper.toPageResponse(brands);
    }
}
