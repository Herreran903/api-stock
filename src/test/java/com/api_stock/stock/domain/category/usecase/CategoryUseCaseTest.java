package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoriesNotFoundByIdsException;
import com.api_stock.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.api_stock.stock.utils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        category.setName(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
        );

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        category.setDescription(
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. " +
                        "Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur rid"
        );

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        category.setName(null);

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        category.setName("");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        category.setDescription(null);

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        category.setDescription("");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExists() {
        when(categoryPersistencePort.isCategoryPresentByName(VALID_CATEGORY_NAME)).thenReturn(true);

        CategoryAlreadyExistException exception = assertThrows(
                CategoryAlreadyExistException.class, () -> categoryUseCase.createCategory(category)
        );

        verify(categoryPersistencePort, times(1)).isCategoryPresentByName(VALID_CATEGORY_NAME);
        assertEquals(CategoryExceptionMessage.ALREADY_EXIST_CATEGORY, exception.getMessage());
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        assertDoesNotThrow(() -> categoryUseCase.createCategory(category));

        verify(categoryPersistencePort).createCategory(category);
    }

    @Test
    void shouldThrowExceptionWhenSortDirectionIsInvalid() {
        String invalidSortDirection = "INVALID";

        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoryUseCase.getCategoriesByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        GlobalConstants.MIN_PAGE_SIZE,
                        invalidSortDirection),
                GlobalExceptionMessage.INVALID_ORDER
        );
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative() {
        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoryUseCase.getCategoriesByPage(
                        -1,
                        GlobalConstants.MIN_PAGE_SIZE,
                        GlobalConstants.ASC),
                GlobalExceptionMessage.NO_NEGATIVE_PAGE
        );
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZeroOrNegative() {
        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoryUseCase.getCategoriesByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        0,
                        GlobalConstants.ASC),
                GlobalExceptionMessage.GREATER_ZERO_SIZE
        );

        assertThrows(
                CategoryNotValidParameterException.class,
                () -> categoryUseCase.getCategoriesByPage(
                        GlobalConstants.MIN_PAGE_NUMBER,
                        -1,
                        GlobalConstants.ASC),
                GlobalExceptionMessage.GREATER_ZERO_SIZE
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"ASC", "DESC"})
    void shouldReturnCategoriesPage(String order) {
        PageData<Category> expectedCategoryPage = new PageData<>(
                List.of(category),
                GlobalConstants.MIN_PAGE_NUMBER,
                1,
                true,
                true,
                false,
                false
        );

        when(categoryPersistencePort.getCategoriesByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                order)).thenReturn(expectedCategoryPage);

        PageData<Category> result = categoryUseCase.getCategoriesByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                order);

        assertEquals(expectedCategoryPage, result);
        verify(categoryPersistencePort, times(1)).getCategoriesByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                order);
    }

    @Test
    void shouldThrowExceptionWhenSomeIdsAreNotFound() {
        List<Long> ids = List.of(VALID_CATEGORY_ID, 2L);
        List<Category> foundCategories = List.of(category);
        List<Long> missingIds = List.of(2L);

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
        List<Long> ids = List.of(VALID_CATEGORY_ID, 2L);
        List<Category> expectedCategories = List.of(
                category,
                new Category(2L, "Books", "Different kinds of books")
        );

        when(categoryPersistencePort.getCategoriesByIds(ids)).thenReturn(expectedCategories);
        List<Category> result = categoryUseCase.getCategoriesByIds(ids);

        assertEquals(expectedCategories, result);
    }
}