package com.api_stock.stock.app.brand.handler;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.app.brand.mapper.IBrandRequestMapper;
import com.api_stock.stock.app.brand.mapper.IBrandResponseMapper;
import com.api_stock.stock.domain.brand.api.IBrandServicePort;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.app.brand.dto.BrandResponse;
import com.api_stock.stock.domain.page.PageData;
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
    private IBrandServicePort brandServicePort;

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

        verify(brandServicePort, times(1)).createBrand(brand);
    }

    @Test
    void shouldReturnBrandPageWhenParametersAreValid() {
        Integer page = 0;
        Integer size = 10;
        String sortDirection = "ASC";

        PageData<Brand> mockBrandPage = mock(PageData.class);
        PageData<BrandResponse> mockResponsePage = mock(PageData.class);

        when(brandServicePort.getBrandsByPage(page, size, sortDirection)).thenReturn(mockBrandPage);
        when(brandResponseMapper.toPageResponse(mockBrandPage)).thenReturn(mockResponsePage);

        PageData<BrandResponse> result = brandHandler.getBrandsByPage(page, size, sortDirection);

        assertNotNull(result);
        assertEquals(mockResponsePage, result);

        verify(brandServicePort).getBrandsByPage(page, size, sortDirection);
        verify(brandResponseMapper).toPageResponse(mockBrandPage);
    }

}