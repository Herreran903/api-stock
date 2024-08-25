package com.api_stock.stock.app.category.mapper;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.domain.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {

    Category toCategory(CategoryRequest categoryRequest);
}
