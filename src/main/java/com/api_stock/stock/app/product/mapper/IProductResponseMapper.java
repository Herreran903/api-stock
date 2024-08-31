package com.api_stock.stock.app.product.mapper;

import com.api_stock.stock.app.product.dto.ProductResponse;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductResponseMapper {

    ProductResponse toResponse(Product product);
    List<ProductResponse> toListResponse(List<Product> products);
    PageData<ProductResponse> toPageResponse(PageData<Product> products);
}
