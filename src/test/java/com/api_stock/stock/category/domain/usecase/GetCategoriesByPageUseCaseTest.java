package com.api_stock.stock.category.domain.usecase;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetCategoriesByPageUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private GetCategoriesByPageUseCase getCategoriesByPageUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCategoriesPage() {
        int page = 0;
        int size = 10;
        String sortDirection = "ASC";

        CategoryPage<Category> expectedCategoryPage = new CategoryPage<>(
                List.of(new Category(1L, "name", "desc")),
                page,
                1,
                true,
                true,
                false,
                false
        );

        when(categoryPersistencePort.getCategoriesByPage(page, size, sortDirection)).thenReturn(expectedCategoryPage);

        CategoryPage<Category> result = getCategoriesByPageUseCase.getCategoriesByPage(page, size, sortDirection);

        assertEquals(expectedCategoryPage, result);
        verify(categoryPersistencePort).getCategoriesByPage(page, size, sortDirection);
    }

}