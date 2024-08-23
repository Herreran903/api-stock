package com.api_stock.stock.brand.app.mapper;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBrandRequestMapper {
    Brand toBrand(BrandRequest brandRequest);
}
