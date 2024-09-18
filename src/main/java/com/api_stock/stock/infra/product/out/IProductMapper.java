package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static com.api_stock.stock.domain.product.util.ProductConstants.BRAND;
import static com.api_stock.stock.domain.product.util.ProductConstants.CATEGORIES;

@Mapper(componentModel = "spring")
public interface IProductMapper {
    @Mapping(source = BRAND, target = BRAND)
    @Mapping(source = CATEGORIES, target = CATEGORIES)
    ProductEntity toEntity(Product product);
    List<Product> toListProduct(List<ProductEntity> productEntities);
    Product toProduct(ProductEntity productEntity);
}
