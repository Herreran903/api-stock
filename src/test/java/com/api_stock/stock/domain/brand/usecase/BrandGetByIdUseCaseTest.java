package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.exception.ex.BrandNotFoundByIdException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BrandGetByIdUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandGetByIdUseCase brandGetByIdUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBrandById_ShouldReturnBrand_WhenBrandExists() {
        Long brandId = 1L;
        Brand expectedBrand = new Brand(brandId, "Nike", "High quality clothing");
        when(brandPersistencePort.getBrandById(brandId)).thenReturn(Optional.of(expectedBrand));

        Brand actualBrand = brandGetByIdUseCase.getBrandById(brandId);

        assertEquals(expectedBrand, actualBrand);
    }

    @Test
    void getBrandByIdShouldThrowBrandNotFoundByIdExceptionWhenBrandDoesNotExist() {

        Long brandId = 1L;
        when(brandPersistencePort.getBrandById(brandId)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundByIdException.class, () -> brandGetByIdUseCase.getBrandById(brandId));
    }


}