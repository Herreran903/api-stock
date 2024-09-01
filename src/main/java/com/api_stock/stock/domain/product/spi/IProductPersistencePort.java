package com.api_stock.stock.domain.product.spi;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;

public interface IProductPersistencePort {
    void createProduct(Product product);
    PageData<Product> getCategoriesByPage(int page, int size, String sortDirection, String sortProperty);
}
