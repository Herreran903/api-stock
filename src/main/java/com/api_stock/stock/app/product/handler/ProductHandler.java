package com.api_stock.stock.app.product.handler;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.mapper.IProductRequestMapper;
import com.api_stock.stock.domain.brand.api.IBrandGetByIdServicePort;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.api.ICategoriesGetByIdsServicePort;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.product.api.IProductCreateServicePort;
import com.api_stock.stock.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductCreateServicePort productCreateServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IBrandGetByIdServicePort brandGetByIdServicePort;
    private final ICategoriesGetByIdsServicePort categoriesGetByIdsServicePort;

    @Override
    public void createProduct(ProductRequest productRequest) {

        Brand brand = brandGetByIdServicePort.getBrandById(productRequest.getBrandId());
        List<Category> categories = categoriesGetByIdsServicePort.getCategoriesByIds(productRequest.getCategoryIds());

        Product product = productRequestMapper.toProduct(productRequest);

        product.setBrand(brand);
        product.setCategories(categories);

        productCreateServicePort.createProduct(product);
    }
}
