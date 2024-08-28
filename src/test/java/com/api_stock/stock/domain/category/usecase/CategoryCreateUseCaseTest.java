package com.api_stock.stock.domain.category.usecase;

import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.category.spi.ICategoryPersistencePort;
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
                "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
                "Devices and gadgets");


        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
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
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Category category = new Category(1L, null, "Devices and gadgets");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Category category = new Category(1L, "", "Devices and gadgets");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        Category category = new Category(1L, "Electronics", null);

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        Category category = new Category(1L, "Electronics", "");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExists() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        when(categoryPersistencePort.isCategoryPresentByName("Electronics")).thenReturn(true);

        CategoryAlreadyExistException exception = assertThrows(
                CategoryAlreadyExistException.class, () -> categoryCreateUseCase.createCategory(category)
        );

        verify(categoryPersistencePort, times(1)).isCategoryPresentByName("Electronics");
        assertEquals(CategoryExceptionMessage.ALREADY_EXIST_CATEGORY, exception.getMessage());
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        assertDoesNotThrow(() -> categoryCreateUseCase.createCategory(category));

        verify(categoryPersistencePort).createCategory(category);
    }
}