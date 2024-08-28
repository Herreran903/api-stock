package com.api_stock.stock.app.category.mapper;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.domain.category.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryRequestMapper {

    @Mapping(target = "id", ignore = true)
    Category toCategory(CategoryRequest categoryRequest);
}
