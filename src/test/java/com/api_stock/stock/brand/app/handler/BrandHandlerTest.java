package com.api_stock.stock.brand.app.handler;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.app.mapper.IBrandRequestMapper;
import com.api_stock.stock.brand.app.mapper.IBrandResponseMapper;
import com.api_stock.stock.brand.domain.api.IBrandCreateServicePort;
import com.api_stock.stock.brand.domain.api.IBrandsGetByPageServicePort;
import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.app.dto.BrandResponse;
import com.api_stock.stock.brand.domain.model.BrandPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BrandHandlerTest {

    @Mock
    private IBrandCreateServicePort brandCreateServicePort;

    @Mock
    private IBrandsGetByPageServicePort brandsGetByPageServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @Mock
    private IBrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandHandler brandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallCreateBrandOnBrandCreateService(){
        BrandRequest brandRequest = new BrandRequest("Nike", "High quality clothing");
        Brand brand = new Brand(1L, "Nike", "High quality clothing");

        when(brandRequestMapper.toBrand(brandRequest)).thenReturn(brand);

        brandHandler.createBrand(brandRequest);

        verify(brandCreateServicePort, times(1)).createBrand(brand);
    }

    @Test
    void shouldReturnBrandPageWhenParametersAreValid() {
        int page = 0;
        int size = 10;
        String sortDirection = "ASC";

        BrandPage<Brand> mockBrandPage = mock(BrandPage.class);
        BrandPage<BrandResponse> mockResponsePage = mock(BrandPage.class);

        when(brandsGetByPageServicePort.getBrandsByPage(page, size, sortDirection)).thenReturn(mockBrandPage);
        when(brandResponseMapper.toPageResponse(mockBrandPage)).thenReturn(mockResponsePage);

        BrandPage<BrandResponse> result = brandHandler.getBrandsByPage(page, size, sortDirection);

        assertNotNull(result);
        assertEquals(mockResponsePage, result);

        verify(brandsGetByPageServicePort).getBrandsByPage(page, size, sortDirection);
        verify(brandResponseMapper).toPageResponse(mockBrandPage);
    }

}