package com.api_stock.stock.domain.product.api;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IProductServicePort {
    void createProduct(Product product);
    PageData<Product> getCategoriesByPage(int page, int size, String order, String sortProperty);
    void updateStock(Long productId, int amount);
    List<String> getListCategoriesOfProducts(List<Long> productIds);
    Integer getStockOfProduct(Long productId);
    PageData<Product> getProductsByPageAndIds(
            Integer page, Integer size, String order, String category, String brand, List<Long> products);
    Map<Long, BigDecimal> getProductsPrice(List<Long> products);
}
