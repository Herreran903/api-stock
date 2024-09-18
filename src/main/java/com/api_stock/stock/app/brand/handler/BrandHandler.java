package com.api_stock.stock.app.brand.handler;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.app.brand.dto.BrandResponse;
import com.api_stock.stock.app.brand.mapper.IBrandRequestMapper;
import com.api_stock.stock.app.brand.mapper.IBrandResponseMapper;
import com.api_stock.stock.domain.brand.api.IBrandServicePort;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.page.PageData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandHandler implements IBrandHandler {
    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapper;
    private final IBrandResponseMapper brandResponseMapper;

    @Override
    public void createBrand(BrandRequest brandRequest) {
        brandServicePort.createBrand(brandRequestMapper.toBrand(brandRequest));
    }

    @Override
    public PageData<BrandResponse> getBrandsByPage(int page, int size, String sortDirection) {
        PageData<Brand> brands = brandServicePort.getBrandsByPage(page, size, sortDirection);

        return brandResponseMapper.toPageResponse(brands);
    }
}
