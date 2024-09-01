package com.api_stock.stock.app.brand.mapper;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.domain.brand.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBrandRequestMapper {

    @Mapping(target = "id", ignore = true)
    Brand toBrand(BrandRequest brandRequest);
}
