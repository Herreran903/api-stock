package com.api_stock.stock.domain.product.usecase;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidFieldException;
import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenCategoriesAreNotUnique() {
        List<Category> categories = List.of(
                new Category(1L, "Category1", "Description1"),
                new Category(1L, "Category1", "Description1"));
        Brand brand = new Brand(1L, "Brand1", "Description1");
        Product product = new Product(1L, "Product1", "", BigDecimal.TEN, 5, brand, categories);
        ProductNotValidFieldException productNotValidFieldException = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );

        assertEquals(ProductExceptionMessage.UNIQUE_CATEGORIES_IDS, productNotValidFieldException.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoriesSizeIsInvalid() {
        List<Category> categories = List.of();
        Brand brand = new Brand(1L, "Brand1", "Description1");
        Product product = new Product(1L, "Product1", "", BigDecimal.TEN, 5, brand, categories);

        ProductNotValidFieldException productNotValidFieldException = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.SIZE_CATEGORIES_ID, productNotValidFieldException.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoriesSizeIsNull() {
        Brand brand = new Brand(1L, "Brand1", "Description1");
        Product product = new Product(1L, "Product1", "", BigDecimal.TEN, 5, brand, null);

        ProductNotValidFieldException productNotValidFieldException = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.EMPTY_CATEGORIES_IDS, productNotValidFieldException.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBrandIsNull() {
        List<Category> categories = List.of(new Category(1L, "Category1", "Description1"));
        Product product = new Product(1L, "Product1", "", BigDecimal.TEN, 5, null, categories);

        ProductNotValidFieldException productNotValidFieldException = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.EMPTY_BRAND_ID, productNotValidFieldException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", ""})
    void shouldThrowExceptionWhenProductNameIsEmpty(String name) {
        String testName = name.equals("null") ? null : name;

        List<Category> categories = List.of(new Category(1L, "Category1", "Description1"));
        Brand brand = new Brand(1L, "Brand1", "Description1");
        Product product = new Product(1L, testName, "", BigDecimal.TEN, 5, brand, categories);

        ProductNotValidFieldException productNotValidFieldException = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.EMPTY_NAME, productNotValidFieldException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "-1"})
    void shouldThrowExceptionWhenStockIsInvalid(String stock) {
        Integer testStock = stock.equals("null") ? null : Integer.parseInt(stock);
        String exceptionMessage = stock.equals("null")
                ? ProductExceptionMessage.EMPTY_STOCK
                : ProductExceptionMessage.NEGATIVE_STOCK;

        List<Category> categories = List.of(new Category(1L, "Category1", "Description1"));
        Brand brand = new Brand(1L, "Brand1", "Description1");
        Product product = new Product(1L, "Product1", "", BigDecimal.TEN, testStock, brand, categories);

        ProductNotValidFieldException productNotValidFieldException = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(exceptionMessage, productNotValidFieldException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "-1"})
    void shouldThrowExceptionWhenPriceIsInvalid(String price) {
        BigDecimal testPrice = price.equals("null") ? null : new BigDecimal(price);
        String exceptionMessage = price.equals("null")
                ? ProductExceptionMessage.EMPTY_PRICE
                : ProductExceptionMessage.NEGATIVE_PRICE;

        List<Category> categories = List.of(new Category(1L, "Category1", "Description1"));
        Brand brand = new Brand(1L, "Brand1", "Description1");
        Product product = new Product(1L, "Product1", "", testPrice, 5, brand, categories);

        ProductNotValidFieldException productNotValidFieldException = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );

        assertEquals(exceptionMessage, productNotValidFieldException.getMessage());
    }

    @Test
    void shouldCreateProductSuccessfully() {
        List<Category> categories = List.of(new Category(1L, "Category1", "Description1"));
        Brand brand = new Brand(1L, "Brand1", "Description1");
        Product product = new Product(1L, "Product1", "", BigDecimal.TEN, 5, brand, categories);

        productUseCase.createProduct(product);

        verify(productPersistencePort, times(1)).createProduct(product);
    }

}