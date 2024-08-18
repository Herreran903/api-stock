package com.api_stock.stock.category.infra.out;

import com.api_stock.stock.category.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryMapper {

    CategoryEntity toEntity(Category category);
    Category toCategory(CategoryEntity categoryEntity);
}
