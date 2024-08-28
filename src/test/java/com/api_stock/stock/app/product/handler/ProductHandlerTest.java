package com.api_stock.stock.app.product.handler;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.mapper.IProductRequestMapper;
import com.api_stock.stock.domain.brand.api.IBrandGetByIdServicePort;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.api.ICategoriesGetByIdsServicePort;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.product.api.IProductCreateServicePort;
import com.api_stock.stock.domain.product.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;

class ProductHandlerTest {

    @Mock
    private IProductCreateServicePort productCreateServicePort;

    @Mock
    private IProductRequestMapper productRequestMapper;

    @Mock
    private IBrandGetByIdServicePort brandGetByIdServicePort;

    @Mock
    private ICategoriesGetByIdsServicePort categoriesGetByIdsServicePort;

    @InjectMocks
    private ProductHandler productHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallCreateProductOnProductCreateService(){
        Long brandId = 1L;
        List<Long> categoriesId = List.of(1L, 2L);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setBrandId(brandId);
        productRequest.setCategoryIds(categoriesId);

        Brand brand = new Brand(brandId, "", "");
        List<Category> categories = List.of(new Category(1L, "", ""), new Category(2L, "", ""));
        Product product = new Product(null, "", "", BigDecimal.ONE, 1, brand, categories);

        when(brandGetByIdServicePort.getBrandById(brandId)).thenReturn(brand);
        when(categoriesGetByIdsServicePort.getCategoriesByIds(categoriesId)).thenReturn(categories);
        when(productRequestMapper.toProduct(productRequest)).thenReturn(product);

        productHandler.createProduct(productRequest);

        verify(brandGetByIdServicePort, times(1)).getBrandById(brandId);
        verify(categoriesGetByIdsServicePort, times(1)).getCategoriesByIds(categoriesId);
        verify(productRequestMapper, times(1)).toProduct(productRequest);
        verify(productCreateServicePort, times(1)).createProduct(product);
    }

}