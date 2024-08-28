package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoriesNotFoundByIdsException;
import com.api_stock.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidFieldException;
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

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        Category category = new Category(
                1L,
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
                "Devices and gadgets");


        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        Category category = new Category(
                1L,
                "Electronics",
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. " +
                        "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur rid");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Category category = new Category(1L, null, "Devices and gadgets");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Category category = new Category(1L, "", "Devices and gadgets");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        Category category = new Category(1L, "Electronics", null);

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        Category category = new Category(1L, "Electronics", "");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExists() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        when(categoryPersistencePort.isCategoryPresentByName("Electronics")).thenReturn(true);

        CategoryAlreadyExistException exception = assertThrows(
                CategoryAlreadyExistException.class, () -> categoryUseCase.createCategory(category)
        );

        verify(categoryPersistencePort, times(1)).isCategoryPresentByName("Electronics");
        assertEquals(CategoryExceptionMessage.ALREADY_EXIST_CATEGORY, exception.getMessage());
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        assertDoesNotThrow(() -> categoryUseCase.createCategory(category));

        verify(categoryPersistencePort).createCategory(category);
    }

    @Test
    void shouldThrowExceptionWhenSortDirectionIsInvalid() {
        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoryUseCase.getCategoriesByPage(0, 10, "INVALID"),
                CategoryExceptionMessage.INVALID_SORT_DIRECTION
        );
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative() {
        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoryUseCase.getCategoriesByPage(-1, 10, "ASC"),
                CategoryExceptionMessage.NO_NEGATIVE_PAGE
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZeroOrNegative() {
        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoryUseCase.getCategoriesByPage(0, 0, "ASC"),
                CategoryExceptionMessage.GREATER_ZERO_SIZE
        );

        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoryUseCase.getCategoriesByPage(0, -1, "ASC"),
                CategoryExceptionMessage.GREATER_ZERO_SIZE
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"ASC", "DESC"})
    void shouldReturnCategoriesPage(String sortDirection) {
        int page = 0;
        int size = 10;

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

        PageData<Category> result = categoryUseCase.getCategoriesByPage(page, size, sortDirection);

        assertEquals(expectedCategoryPage, result);
        verify(categoryPersistencePort, times(1)).getCategoriesByPage(page, size, sortDirection);
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
                () -> categoryUseCase.getCategoriesByIds(ids)
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
        List<Category> result = categoryUseCase.getCategoriesByIds(ids);

        assertEquals(expectedCategories, result);
    }
}