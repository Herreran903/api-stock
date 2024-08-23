package com.api_stock.stock.brand.app.handler;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.app.mapper.IBrandRequestMapper;
import com.api_stock.stock.brand.app.mapper.IBrandResponseMapper;
import com.api_stock.stock.brand.domain.api.IBrandCreateServicePort;
import com.api_stock.stock.brand.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class BrandHandlerTest {

    @Mock
    private IBrandCreateServicePort brandCreateServicePort;

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



}