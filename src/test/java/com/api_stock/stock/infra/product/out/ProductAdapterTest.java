package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.api_stock.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class ProductAdapterTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IProductMapper productMapper;

    @InjectMocks
    private ProductAdapter productAdapter;

    private Product product;
    private ProductEntity productEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product(
                VALID_PRODUCT_ID,
                VALID_PRODUCT_NAME,
                VALID_PRODUCT_DESCRIPTION,
                VALID_PRODUCT_PRICE,
                VALID_PRODUCT_STOCK,
                null,
                null);

        productEntity = new ProductEntity(
                VALID_PRODUCT_ID,
                VALID_PRODUCT_NAME,
                VALID_PRODUCT_DESCRIPTION,
                VALID_PRODUCT_PRICE,
                VALID_PRODUCT_STOCK,
                null,
                null
        );
    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(productMapper.toEntity(any(Product.class))).thenReturn(productEntity);

        productAdapter.createProduct(product);

        verify(productMapper, times(1)).toEntity(product);
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void shouldReturnProductsPage() {
        //Arrange
        int page = GlobalConstants.MIN_PAGE_NUMBER;
        int size = GlobalConstants.MIN_PAGE_SIZE;
        String orderDirection = GlobalConstants.ASC;
        String orderProperty = ProductConstants.NAME;

        List<ProductEntity> entities = List.of(productEntity, productEntity);
        Page<ProductEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), entities.size());
        when(productRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

        List<Product> products = List.of(product, product);
        when(productMapper.toListProduct(entities)).thenReturn(products);

        //Act
        PageData<Product> result = productAdapter.getCategoriesByPage(page, size, orderDirection, orderProperty);

        //Assert
        assertEquals(entities.size(), result.getData().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(entities.size(), result.getTotalElements());
        assertTrue(result.isFirst());
        assertFalse(result.isLast());
        assertTrue(result.isHasNext());
        assertFalse(result.isHasPrevious());

        verify(productRepository).findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        verify(productMapper).toListProduct(entities);
    }

    @Test
    void shouldReturnProductSuccessfully() {
        when(productRepository.findById(VALID_PRODUCT_ID)).thenReturn(Optional.of(productEntity));
        when(productMapper.toProduct(productEntity)).thenReturn(product);

        Optional<Product> result = productAdapter.getProductById(VALID_PRODUCT_ID);

        assertTrue(result.isPresent());
        assertEquals(VALID_PRODUCT_ID, result.get().getId());

        verify(productRepository, times(1)).findById(VALID_PRODUCT_ID);
        verify(productMapper, times(1)).toProduct(productEntity);
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        when(productRepository.findById(VALID_PRODUCT_ID)).thenReturn(Optional.empty());

        Optional<Product> result = productAdapter.getProductById(VALID_PRODUCT_ID);

        assertFalse(result.isPresent());

        verify(productRepository, times(1)).findById(VALID_PRODUCT_ID);
        verify(productMapper, never()).toProduct(any(ProductEntity.class));
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        when(productMapper.toEntity(any(Product.class))).thenReturn(productEntity);

        productAdapter.updateProduct(product);

        verify(productMapper, times(1)).toEntity(product);
        verify(productRepository, times(1)).save(productEntity);
    }

}