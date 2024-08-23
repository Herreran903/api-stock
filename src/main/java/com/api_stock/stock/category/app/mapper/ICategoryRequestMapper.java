package com.api_stock.stock.category.app.mapper;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {

    Brand toCategory(CategoryRequest categoryRequest);
}
