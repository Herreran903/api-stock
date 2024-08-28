package com.api_stock.stock.infra.brand.out;

import com.api_stock.stock.domain.brand.model.Brand;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBrandMapper {

    BrandEntity toEntity(Brand brand);
    Brand toBrand(BrandEntity brandEntity);
    List<Brand> toBrandsList(List<BrandEntity> brandEntities);
}
