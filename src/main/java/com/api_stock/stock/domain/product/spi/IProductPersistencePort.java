package com.api_stock.stock.domain.product.spi;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;

import java.util.Optional;

public interface IProductPersistencePort {
    void createProduct(Product product);
    PageData<Product> getCategoriesByPage(int page, int size, String sortDirection, String sortProperty);
    Optional<Product> getProductById(Long id);
    void updateProduct(Product product);
}
