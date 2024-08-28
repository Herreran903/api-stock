package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "categories", target = "categories")
    ProductEntity toEntity(Product product);
}
