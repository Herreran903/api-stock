package com.api_stock.stock.app.brand.mapper;

import com.api_stock.stock.app.brand.dto.BrandResponse;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.page.PageData;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBrandResponseMapper {
    BrandResponse toResponse(Brand brand);
    List<BrandResponse> toListResponse(List<Brand> brands);
    PageData<BrandResponse> toPageResponse(PageData<Brand> brandPage);
}
