package com.api_stock.stock.app.category.mapper;

import com.api_stock.stock.app.category.dto.CategoryResponse;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {

    CategoryResponse toResponse(Category category);
    List<CategoryResponse> toListResponse(List<Category> categories);
    PageData<CategoryResponse> toPageResponse(PageData<Category> categoryPage);
}
