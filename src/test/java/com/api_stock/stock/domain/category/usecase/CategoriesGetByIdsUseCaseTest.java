package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoriesNotFoundByIdsException;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CategoriesGetByIdsUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoriesGetByIdsUseCase categoriesGetByIdsUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenSomeIdsAreNotFound() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<Category> foundCategories = List.of(
                new Category(1L, "Electronics", "Devices and gadgets"),
                new Category(2L, "Books", "Different kinds of books")
        );
        List<Long> missingIds = List.of(3L);

        when(categoryPersistencePort.getCategoriesByIds(ids)).thenReturn(foundCategories);

        CategoriesNotFoundByIdsException exception = assertThrows(
                CategoriesNotFoundByIdsException.class,
                () -> categoriesGetByIdsUseCase.getCategoriesByIds(ids)
        );

        assertEquals(CategoryExceptionMessage.NO_FOUND_CATEGORIES, exception.getMessage());
        assertEquals(missingIds, exception.getMissingIds());
    }

    @Test
    void shouldReturnCategoriesWhenAllIdsAreFound() {
        List<Long> ids = List.of(1L, 2L);
        List<Category> expectedCategories = List.of(
                new Category(1L, "Electronics", "Devices and gadgets"),
                new Category(2L, "Books", "Different kinds of books")
        );

        when(categoryPersistencePort.getCategoriesByIds(ids)).thenReturn(expectedCategories);
        List<Category> result = categoriesGetByIdsUseCase.getCategoriesByIds(ids);

        assertEquals(expectedCategories, result);
    }
}