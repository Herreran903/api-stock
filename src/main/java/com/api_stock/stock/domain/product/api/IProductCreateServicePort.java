package com.api_stock.stock.domain.product.api;

import com.api_stock.stock.domain.product.model.Product;

public interface IProductCreateServicePort {
    void createProduct(Product product);
}
