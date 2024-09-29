package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

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
    default PageData<Product> toPageData(Page<ProductEntity> productEntityPageData){
        return new PageData<>(
                toListProduct(productEntityPageData.getContent()),
                productEntityPageData.getNumber(),
                (int) productEntityPageData.getTotalElements(),
                productEntityPageData.isFirst(),
                productEntityPageData.isLast(),
                productEntityPageData.hasNext(),
                productEntityPageData.hasPrevious()
        );
    }
}
