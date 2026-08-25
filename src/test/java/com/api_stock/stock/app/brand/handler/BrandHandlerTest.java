package com.api_stock.stock.app.brand.handler;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.app.brand.mapper.IBrandRequestMapper;
import com.api_stock.stock.app.brand.mapper.IBrandResponseMapper;
import com.api_stock.stock.domain.brand.api.IBrandServicePort;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.app.brand.dto.BrandResponse;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.api_stock.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BrandHandlerTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @Mock
    private IBrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandHandler brandHandler;

    private Brand brand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }

    @Test
    void shouldCallCreateBrandOnBrandCreateService(){
        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setName(VALID_BRAND_NAME);
        brandRequest.setDescription(VALID_BRAND_DESCRIPTION);

        when(brandRequestMapper.toBrand(brandRequest)).thenReturn(brand);

        brandHandler.createBrand(brandRequest);

        verify(brandServicePort, times(1)).createBrand(brand);
    }

    @Test
    void shouldReturnBrandPageWhenParametersAreValid() {
        int page = GlobalConstants.MIN_PAGE_NUMBER;
        int size = Integer.parseInt(GlobalConstants.DEFAULT_PAGE_SIZE);
        String order = GlobalConstants.ASC;

        PageData<Brand> mockBrandPage = mock(PageData.class);
        PageData<BrandResponse> mockResponsePage = mock(PageData.class);

        when(brandServicePort.getBrandsByPage(page, size, order)).thenReturn(mockBrandPage);
        when(brandResponseMapper.toPageResponse(mockBrandPage)).thenReturn(mockResponsePage);

        PageData<BrandResponse> result = brandHandler.getBrandsByPage(page, size, order);

        assertNotNull(result);
        assertEquals(mockResponsePage, result);

        verify(brandServicePort).getBrandsByPage(page, size, order);
        verify(brandResponseMapper).toPageResponse(mockBrandPage);
    }

}