package com.api_stock.stock.app.product.handler;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.mapper.IProductRequestMapper;
import com.api_stock.stock.domain.brand.api.IBrandServicePort;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.api.ICategoryServicePort;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.product.api.IProductServicePort;
import com.api_stock.stock.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {

    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IBrandServicePort brandServicePort;
    private final ICategoryServicePort categoryServicePort;

    @Override
    public void createProduct(ProductRequest productRequest) {

        Brand brand = brandServicePort.getBrandById(productRequest.getBrandId());
        List<Category> categories = categoryServicePort.getCategoriesByIds(productRequest.getCategoryIds());

        Product product = productRequestMapper.toProduct(productRequest);

        product.setBrand(brand);
        product.setCategories(categories);

        productServicePort.createProduct(product);
    }
}
