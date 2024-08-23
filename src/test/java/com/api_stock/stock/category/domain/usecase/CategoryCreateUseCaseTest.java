package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.exception.ExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryCreateUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryCreateUseCase categoryCreateUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        Category category = new Category(
                1L,
                "123457891234578912345789123457891234578912345789123457891234578912345789123457891234578912345789",
                "Description");


        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(ExceptionMessage.TOO_LONG_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        Category category = new Category(
                1L,
                "Name",
                "123457891234578912345789123457891234578912345789123457891234578912345789123457891234578912345789");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(ExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Category category = new Category(1L, null, "Description");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(ExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Category category = new Category(1L, "", "Description");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(ExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        Category category = new Category(1L, "Name", null);

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(ExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        Category category = new Category(1L, "Name", "");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(ExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExists() {
        Category category = new Category(1L, "ExistingCategory", "Description");

        when(categoryPersistencePort.isCategoryPresentByName("ExistingCategory")).thenReturn(true);

        CategoryAlreadyExistException exception = assertThrows(
                CategoryAlreadyExistException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        verify(categoryPersistencePort, times(1)).isCategoryPresentByName("ExistingCategory");
        assertEquals(ExceptionMessage.ALREADY_EXIST_CATEGORY, exception.getMessage());
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        Category category = new Category(1L, "Name", "Description");

        assertDoesNotThrow(() -> categoryCreateUseCase.createCategory(category));

        verify(categoryPersistencePort).createCategory(category);
    }
}