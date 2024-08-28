package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class ProductAdapterTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IProductMapper productMapper;

    @InjectMocks
    private ProductAdapter productAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProductSuccessfully() {
        Product product = new Product(1L, "ProductName", "Description", BigDecimal.valueOf(10.0), 5, null, null);
        ProductEntity productEntity = new ProductEntity();

        when(productMapper.toEntity(any(Product.class))).thenReturn(productEntity);

        productAdapter.createProduct(product);

        verify(productMapper, times(1)).toEntity(product);
        verify(productRepository, times(1)).save(productEntity);
    }

}