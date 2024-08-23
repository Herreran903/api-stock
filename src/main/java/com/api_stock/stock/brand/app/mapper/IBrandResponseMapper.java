package com.api_stock.stock.brand.app.mapper;

import com.api_stock.stock.brand.app.dto.BrandResponse;
import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.model.BrandPage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBrandResponseMapper {
    BrandResponse toResponse(Brand brand);
    List<BrandResponse> toListResponse(List<Brand> brands);
    BrandPage<BrandResponse> toPageResponse(BrandPage<Brand> brandPage);
}
