package com.api_stock.stock.app.product.mapper;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "brand", ignore = true)
    Product toProduct(ProductRequest productRequest);
}
