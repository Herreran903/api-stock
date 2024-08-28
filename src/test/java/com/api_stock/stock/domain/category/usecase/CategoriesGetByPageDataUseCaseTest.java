package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriesGetByPageDataUseCaseTest {

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

    @ParameterizedTest
    @ValueSource(strings = {"ASC", "DESC"})
    void shouldReturnCategoriesPage(String sortDirection) {
        Integer page = 0;
        Integer size = 10;

        PageData<Category> expectedCategoryPage = new PageData<>(
                List.of(new Category(1L, "Electronics", "Devices and gadgets")),
                page,
                1,
                true,
                true,
                false,
                false
        );

        when(categoryPersistencePort.getCategoriesByPage(page, size, sortDirection)).thenReturn(expectedCategoryPage);

        PageData<Category> result = categoriesGetByPageUseCase.getCategoriesByPage(page, size, sortDirection);

        assertEquals(expectedCategoryPage, result);
        verify(categoryPersistencePort, times(1)).getCategoriesByPage(page, size, sortDirection);
    }

}