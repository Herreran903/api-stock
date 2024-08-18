package com.api_stock.stock.category.app.mapper;

import com.api_stock.stock.category.app.dto.CategoryResponse;
import com.api_stock.stock.category.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {

    CategoryResponse toResponse(Category category);
}
