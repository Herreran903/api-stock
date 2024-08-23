package com.api_stock.stock.category.infra.out;

import com.api_stock.stock.category.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryMapper {

    CategoryEntity toEntity(Brand brand);
    Brand toCategory(CategoryEntity categoryEntity);
    List<Brand> toCategoriesList(List<CategoryEntity> categoryEntities);
}
