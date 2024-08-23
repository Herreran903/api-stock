package com.api_stock.stock.brand.domain.usecase;

import com.api_stock.stock.brand.domain.exception.BrandExceptionMessage;
import com.api_stock.stock.brand.domain.exception.ex.BrandNotValidParameterException;
import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.model.BrandPage;
import com.api_stock.stock.brand.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandsGetByPageUseCaseTest {
    
    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandsGetByPageUseCase categoriesGetByPageUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenSortDirectionIsInvalid() {
        assertThrows(
                BrandNotValidParameterException.class,
                () -> categoriesGetByPageUseCase.getBrandsByPage(0, 10, "INVALID"),
                BrandExceptionMessage.INVALID_SORT_DIRECTION
        );
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative() {
        assertThrows(
                BrandNotValidParameterException.class,
                () -> categoriesGetByPageUseCase.getBrandsByPage(-1, 10, "ASC"),
                BrandExceptionMessage.NO_NEGATIVE_PAGE
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZeroOrNegative() {
        assertThrows(
                BrandNotValidParameterException.class,
                () -> categoriesGetByPageUseCase.getBrandsByPage(0, 0, "ASC"),
                BrandExceptionMessage.GREATER_ZERO_SIZE
        );

        assertThrows(
                BrandNotValidParameterException.class,
                () -> categoriesGetByPageUseCase.getBrandsByPage(0, -1, "ASC"),
                BrandExceptionMessage.GREATER_ZERO_SIZE
        );
    }

    @Test
    void shouldReturnBrandsPage() {
        int page = 0;
        int size = 10;
        String sortDirection = "ASC";

        BrandPage<Brand> expectedBrandPage = new BrandPage<>(
                List.of(new Brand(1L, "Nike", "High quality clothing")),
                page,
                1,
                true,
                true,
                false,
                false
        );

        when(brandPersistencePort.getBrandsByPage(page, size, sortDirection)).thenReturn(expectedBrandPage);

        BrandPage<Brand> result = categoriesGetByPageUseCase.getBrandsByPage(page, size, sortDirection);

        assertEquals(expectedBrandPage, result);
        verify(brandPersistencePort, times(1)).getBrandsByPage(page, size, sortDirection);
    }

}