package com.api_stock.stock.app.product.handler;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.dto.ProductResponse;
import com.api_stock.stock.app.product.mapper.IProductRequestMapper;
import com.api_stock.stock.app.product.mapper.IProductResponseMapper;
import com.api_stock.stock.domain.brand.api.IBrandServicePort;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.api.ICategoryServicePort;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.api.IProductServicePort;
import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.api_stock.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductHandlerTest {

    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private IProductRequestMapper productRequestMapper;

    @Mock
    private IProductResponseMapper productResponseMapper;

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private ICategoryServicePort categoryServicePort;

    @InjectMocks
    private ProductHandler productHandler;

    private Category category;
    private List<Category> categories;
    private Brand brand;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        categories = List.of(category);
        brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
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
    void shouldCallCreateProductOnProductCreateService(){
        List<Long> categoriesId = List.of(VALID_CATEGORY_ID);
        ProductRequest productRequest = new ProductRequest();
        productRequest.setBrandId(VALID_BRAND_ID);
        productRequest.setCategoryIds(List.of(VALID_CATEGORY_ID));

        when(brandServicePort.getBrandById(VALID_BRAND_ID)).thenReturn(brand);
        when(categoryServicePort.getCategoriesByIds(categoriesId)).thenReturn(categories);
        when(productRequestMapper.toProduct(productRequest)).thenReturn(product);

        productHandler.createProduct(productRequest);

        verify(brandServicePort, times(1)).getBrandById(VALID_BRAND_ID);
        verify(categoryServicePort, times(1)).getCategoriesByIds(categoriesId);
        verify(productRequestMapper, times(1)).toProduct(productRequest);
        verify(productServicePort, times(1)).createProduct(product);
    }

    @Test
    void shouldReturnProductPageWhenParametersAreValid() {
        int page = GlobalConstants.MIN_PAGE_NUMBER;
        int size = Integer.parseInt(GlobalConstants.DEFAULT_PAGE_SIZE);
        String sortDirection = GlobalConstants.ASC;
        String sortProperty = ProductConstants.NAME;

        PageData<Product> mockProductPage = mock(PageData.class);
        PageData<ProductResponse> mockResponsePage = mock(PageData.class);

        when(productServicePort.getCategoriesByPage(page, size, sortDirection, sortProperty)).thenReturn(mockProductPage);
        when(productResponseMapper.toPageResponse(mockProductPage)).thenReturn(mockResponsePage);

        PageData<ProductResponse> result = productHandler.getProductsByPage(page, size, sortDirection, sortProperty);

        assertNotNull(result);
        assertEquals(mockResponsePage, result);

        verify(productServicePort).getCategoriesByPage(page, size, sortDirection, sortProperty);
        verify(productResponseMapper).toPageResponse(mockProductPage);
    }

}