package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    @Mapping(source = "brand", target = "brand")
    @Mapping(source = "categories", target = "categories")
    ProductEntity toEntity(Product product);

    List<Product> toListProduct(List<ProductEntity> productEntities);
}
