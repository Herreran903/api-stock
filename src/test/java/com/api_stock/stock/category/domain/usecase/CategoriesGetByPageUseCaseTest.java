package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.exception.CategoryExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriesGetByPageUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoriesGetByPageUseCase categoriesGetByPageUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenSortDirectionIsInvalid() {
        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoriesGetByPageUseCase.getCategoriesByPage(0, 10, "INVALID"),
                CategoryExceptionMessage.INVALID_SORT_DIRECTION
        );
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative() {
        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoriesGetByPageUseCase.getCategoriesByPage(-1, 10, "ASC"),
                CategoryExceptionMessage.NO_NEGATIVE_PAGE
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZeroOrNegative() {
        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoriesGetByPageUseCase.getCategoriesByPage(0, 0, "ASC"),
                CategoryExceptionMessage.GREATER_ZERO_SIZE
        );

        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoriesGetByPageUseCase.getCategoriesByPage(0, -1, "ASC"),
                CategoryExceptionMessage.GREATER_ZERO_SIZE
        );
    }

    @Test
    void shouldReturnCategoriesPage() {
        int page = 0;
        int size = 10;
        String sortDirection = "ASC";

        CategoryPage<Category> expectedCategoryPage = new CategoryPage<>(
                List.of(new Category(1L, "Electronics", "Devices and gadgets")),
                page,
                1,
                true,
                true,
                false,
                false
        );

        when(categoryPersistencePort.getCategoriesByPage(page, size, sortDirection)).thenReturn(expectedCategoryPage);

        CategoryPage<Category> result = categoriesGetByPageUseCase.getCategoriesByPage(page, size, sortDirection);

        assertEquals(expectedCategoryPage, result);
        verify(categoryPersistencePort, times(1)).getCategoriesByPage(page, size, sortDirection);
    }

}