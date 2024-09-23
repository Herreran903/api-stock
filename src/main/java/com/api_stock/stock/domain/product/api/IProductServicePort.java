package com.api_stock.stock.domain.product.api;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;

import java.util.List;

public interface IProductServicePort {
    void createProduct(Product product);
    PageData<Product> getCategoriesByPage(int page, int size, String sortDirection, String sortProperty);
    void updateStock(Long productId, int amount);
    List<String> getListCategoriesOfProducts(List<Long> productIds);
    Integer getStockOfProduct(Long productId);
}
