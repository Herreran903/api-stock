package com.api_stock.stock.domain.product.model;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.model.Category;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldUpdateProductSuccessfully() {
        Product product = new Product(null, null, null, null, null, null, null);
        Long id = 1L;
        String name = "Product Name";
        String description = "Product Description";
        BigDecimal price = BigDecimal.valueOf(99.99);
        Integer stock = 10;
        Brand brand = new Brand(1L, "", "");
        List<Category> categories = List.of(new Category(1L, "", ""));

        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setBrand(brand);
        product.setCategories(categories);

        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(stock, product.getStock());
        assertEquals(brand, product.getBrand());
        assertEquals(categories, product.getCategories());
    }

}