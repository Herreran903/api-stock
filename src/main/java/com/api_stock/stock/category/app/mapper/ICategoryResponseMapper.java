package com.api_stock.stock.category.app.mapper;

import com.api_stock.stock.category.app.dto.CategoryResponse;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICategoryResponseMapper {

    CategoryResponse toResponse(Category category);
    List<CategoryResponse> toListResponse(List<Category> categories);
    CategoryPage<CategoryResponse> toPageResponse(CategoryPage<Category> categoryPage);
}
