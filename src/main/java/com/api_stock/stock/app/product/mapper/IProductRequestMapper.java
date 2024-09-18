package com.api_stock.stock.app.product.mapper;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.api_stock.stock.domain.product.util.ProductConstants.*;

@Mapper(componentModel = "spring")
public interface IProductRequestMapper {
    @Mapping(target = PRODUCT_ID, ignore = true)
    @Mapping(target = CATEGORIES, ignore = true)
    @Mapping(target = BRAND, ignore = true)
    Product toProduct(ProductRequest productRequest);
}
