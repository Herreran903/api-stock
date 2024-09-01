package com.api_stock.stock.domain.product.api;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;

public interface IProductServicePort {
    void createProduct(Product product);
    PageData<Product> getCategoriesByPage(int page, int size, String sortDirection, String sortProperty);
}
