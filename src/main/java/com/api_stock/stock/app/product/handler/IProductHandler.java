package com.api_stock.stock.app.product.handler;

import com.api_stock.stock.app.product.dto.ProductRequest;

public interface IProductHandler {
    void createProduct(ProductRequest productRequest);
}
