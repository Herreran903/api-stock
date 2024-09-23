package com.api_stock.stock.domain.product.usecase;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.exception.ex.ProductNotFoundByIdException;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidFieldException;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidParameterException;
import com.api_stock.stock.domain.product.exception.ex.StockNotValidFieldException;
import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.api_stock.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Category category;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        List<Category> categories = List.of(category);
        Brand brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        product = new Product(
                VALID_PRODUCT_ID,
                VALID_PRODUCT_NAME,
                VALID_PRODUCT_DESCRIPTION,
                VALID_PRODUCT_PRICE,
                VALID_PRODUCT_STOCK,
                brand,
                categories);
    }

    @Test
    void shouldThrowExceptionWhenCategoriesAreNotUnique() {
        List<Category> categories = List.of(category, category);
        product.setCategories(categories);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );

        assertEquals(ProductExceptionMessage.UNIQUE_CATEGORIES_IDS, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoriesSizeIsInvalid() {
        List<Category> categories = List.of();
        product.setCategories(categories);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.SIZE_CATEGORIES_ID, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoriesSizeIsNull() {
        product.setCategories(null);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.EMPTY_CATEGORIES_IDS, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenBrandIsNull() {
        product.setBrand(null);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.EMPTY_BRAND_ID, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", ""})
    void shouldThrowExceptionWhenProductNameIsEmpty(String name) {
        String testName = name.equals("null") ? null : name;

        product.setName(testName);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(ProductExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "-1"})
    void shouldThrowExceptionWhenStockIsInvalid(String stock) {
        Integer testStock = stock.equals("null") ? null : Integer.parseInt(stock);
        String exceptionMessage = stock.equals("null")
                ? ProductExceptionMessage.EMPTY_STOCK
                : ProductExceptionMessage.NEGATIVE_STOCK;

        product.setStock(testStock);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );
        assertEquals(exceptionMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "-1"})
    void shouldThrowExceptionWhenPriceIsInvalid(String price) {
        BigDecimal testPrice = price.equals("null") ? null : new BigDecimal(price);
        String exceptionMessage = price.equals("null")
                ? ProductExceptionMessage.EMPTY_PRICE
                : ProductExceptionMessage.NEGATIVE_PRICE;

        product.setPrice(testPrice);

        ProductNotValidFieldException exception = assertThrows(
                ProductNotValidFieldException.class, () -> productUseCase.createProduct(product)
        );

        assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    void shouldCreateProductSuccessfully() {
        productUseCase.createProduct(product);

        verify(productPersistencePort, times(1)).createProduct(product);
    }

    @Test
    void shouldThrowExceptionWhenSortDirectionIsInvalid() {
        String invalidSortDirection = "INVALID";

        assertThrows(
                ProductNotValidParameterException.class,
                () -> productUseCase.getCategoriesByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        GlobalConstants.MIN_PAGE_SIZE,
                        invalidSortDirection,
                        ProductConstants.NAME),
                GlobalExceptionMessage.INVALID_SORT_DIRECTION
        );
    }

    @Test
    void shouldThrowExceptionWhenSortPropertyIsInvalid() {
        String invalidSortProperty = "INVALID";

        assertThrows(
                ProductNotValidParameterException.class,
                () -> productUseCase.getCategoriesByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        GlobalConstants.MIN_PAGE_SIZE,
                        GlobalConstants.ASC,
                        invalidSortProperty),
                GlobalExceptionMessage.INVALID_SORT_DIRECTION
        );
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative() {
        assertThrows(
                ProductNotValidParameterException.class,
                () -> productUseCase.getCategoriesByPage(
                        -1,
                        GlobalConstants.MIN_PAGE_SIZE,
                        GlobalConstants.ASC,
                        ProductConstants.NAME),
                GlobalExceptionMessage.NO_NEGATIVE_PAGE
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZeroOrNegative() {
        assertThrows(
                ProductNotValidParameterException.class,
                () -> productUseCase.getCategoriesByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        0,
                        GlobalConstants.ASC,
                        ProductConstants.NAME),
                GlobalExceptionMessage.GREATER_ZERO_SIZE
        );

        assertThrows(
                ProductNotValidParameterException.class,
                () -> productUseCase.getCategoriesByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        -1,
                        GlobalConstants.ASC,
                        ProductConstants.NAME),
                GlobalExceptionMessage.GREATER_ZERO_SIZE
        );
    }

    @ParameterizedTest
    @MethodSource("sortDirectionAndPropertyProvider")
    void shouldReturnCategoriesPage(String sortDirection, String sortProperty) {
        PageData<Product> expectedCategoryPage = new PageData<>(
                List.of(product),
                GlobalConstants.MIN_PAGE_NUMBER,
                1,
                true,
                true,
                false,
                false
        );

        when(productPersistencePort.getCategoriesByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                sortDirection,
                sortProperty)).thenReturn(expectedCategoryPage);

        PageData<Product> result = productUseCase.getCategoriesByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                sortDirection,
                sortProperty);

        assertEquals(expectedCategoryPage, result);
        verify(productPersistencePort, times(1)).getCategoriesByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                sortDirection,
                sortProperty);
    }

    @Test
    void shouldThrowExceptionWhenProductIdIsNull() {
        Long productId = null;

        StockNotValidFieldException exception = assertThrows(
                StockNotValidFieldException.class, () -> productUseCase.updateStock(productId, VALID_PRODUCT_STOCK)
        );

        assertEquals(ProductExceptionMessage.EMPTY_PRODUCT, exception.getMessage());
        verify(productPersistencePort, never()).getProductById(anyLong());
        verify(productPersistencePort, never()).updateProduct(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long productId = 9999L;

        when(productPersistencePort.getProductById(productId)).thenReturn(Optional.empty());

        ProductNotFoundByIdException exception = assertThrows(
                ProductNotFoundByIdException.class, () -> productUseCase.updateStock(productId, VALID_PRODUCT_STOCK)
        );

        assertEquals(ProductExceptionMessage.NO_FOUND_PRODUCT, exception.getMessage());
        verify(productPersistencePort,times(1)).getProductById(productId);
        verify(productPersistencePort, never()).updateProduct(any(Product.class));
    }

    @Test
    void shouldUpdateStockProductSuccessfully() {
        when(productPersistencePort.getProductById(VALID_PRODUCT_ID)).thenReturn(Optional.ofNullable(product));

        productUseCase.updateStock(VALID_PRODUCT_ID, VALID_PRODUCT_STOCK);

        verify(productPersistencePort, times(1)).getProductById(VALID_PRODUCT_ID);
        verify(productPersistencePort, times(1)).updateProduct(product);
    }

    @Test
    void shouldGetCategoriesProductsSuccessfully() {
        List<Long> productIds = List.of(1L, 2L, 3L);
        List<String> expectedCategoryIds = List.of("Category1", "Category2", "Category3");

        when(productPersistencePort.getProductById(anyLong())).thenReturn(Optional.of(product));

        when(productPersistencePort.getListCategoriesOfProducts(productIds)).thenReturn(expectedCategoryIds);

        List<String> result = productUseCase.getListCategoriesOfProducts(productIds);

        assertEquals(expectedCategoryIds, result);
        verify(productPersistencePort, times(3)).getProductById(anyLong());
        verify(productPersistencePort, times(1)).getListCategoriesOfProducts(productIds);
    }


    @Test
    void shouldGetStockProductSuccessfully() {
        when(productPersistencePort.getProductById(VALID_PRODUCT_ID)).thenReturn(Optional.ofNullable(product));

        Integer result = productUseCase.getStockOfProduct(VALID_PRODUCT_ID);

        assertEquals(VALID_PRODUCT_STOCK, result);
        verify(productPersistencePort, times(1)).getProductById(VALID_PRODUCT_ID);
    }


    private static Stream<Arguments> sortDirectionAndPropertyProvider() {
        return Stream.of(
                Arguments.of(GlobalConstants.ASC, ProductConstants.NAME),
                Arguments.of(GlobalConstants.ASC, ProductConstants.BRAND),
                Arguments.of(GlobalConstants.ASC, ProductConstants.CATEGORIES),
                Arguments.of(GlobalConstants.DESC, ProductConstants.NAME),
                Arguments.of(GlobalConstants.DESC, ProductConstants.BRAND),
                Arguments.of(GlobalConstants.DESC, ProductConstants.CATEGORIES)
        );
    }
}