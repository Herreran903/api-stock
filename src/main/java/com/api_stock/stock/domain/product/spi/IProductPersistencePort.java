package com.api_stock.stock.domain.product.spi;

import com.api_stock.stock.domain.product.model.Product;

public interface IProductPersistencePort {
    void createProduct(Product product);
}
