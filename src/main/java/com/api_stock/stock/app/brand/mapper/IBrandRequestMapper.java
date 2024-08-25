package com.api_stock.stock.app.brand.mapper;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.domain.brand.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBrandRequestMapper {
    Brand toBrand(BrandRequest brandRequest);
}
