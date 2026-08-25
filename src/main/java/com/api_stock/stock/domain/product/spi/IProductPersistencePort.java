package com.api_stock.stock.domain.product.spi;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductPersistencePort {
    void createProduct(Product product);

    PageData<Product> getCategoriesByPage(int page, int size, String order, String sortProperty);

    Optional<Product> getProductById(Long id);

    void updateProduct(Product product);

    List<String> getListCategoriesOfProducts(List<Long> productIds);

    PageData<Product> getProductsByPageAndIds(
            Integer page, Integer size, String order, String category, String brand, List<Long> products);
    List<Product> getAllProductsByIds(List<Long> products);
}
