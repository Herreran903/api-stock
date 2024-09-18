package com.api_stock.stock.app.brand.mapper;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.domain.brand.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.api_stock.stock.domain.brand.util.BrandConstants.BRAND_ID;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {
    @Mapping(target = BRAND_ID, ignore = true)
    Brand toBrand(BrandRequest brandRequest);
}
