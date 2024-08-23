package com.api_stock.stock.brand.infra.out;

import com.api_stock.stock.brand.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBrandMapper {
    BrandEntity toEntity(Brand brand);
    Brand toBrand(BrandEntity brandEntity);
}
