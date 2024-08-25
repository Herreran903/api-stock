package com.api_stock.stock.domain.brand.usecase;

import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.exception.ex.BrandNotValidParameterException;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandsGetByPageDataUseCaseTest {
    
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

        PageData<Brand> expectedBrandPage = new PageData<>(
                List.of(new Brand(1L, "Nike", "High quality clothing")),
                page,
                1,
                true,
                true,
                false,
                false
        );

        when(brandPersistencePort.getBrandsByPage(page, size, sortDirection)).thenReturn(expectedBrandPage);

        PageData<Brand> result = categoriesGetByPageUseCase.getBrandsByPage(page, size, sortDirection);

        assertEquals(expectedBrandPage, result);
        verify(brandPersistencePort, times(1)).getBrandsByPage(page, size, sortDirection);
    }

}