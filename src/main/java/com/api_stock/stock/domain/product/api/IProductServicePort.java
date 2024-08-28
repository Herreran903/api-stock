package com.api_stock.stock.domain.product.api;

import com.api_stock.stock.domain.product.model.Product;

public interface IProductServicePort {
    void createProduct(Product product);
}
