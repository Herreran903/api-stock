package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.exception.CategoryExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.category.domain.model.Brand;
import com.api_stock.stock.category.domain.spi.IBrandPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandCreateUseCaseTest {

    @Mock
    private IBrandPersistencePort categoryPersistencePort;

    @InjectMocks
    private BrandCreateUseCase categoryCreateUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenNameExceedsMaxLength() {
        Brand brand = new Brand(
                1L,
                "123457891234578912345789123457891234578912345789123457891234578912345789123457891234578912345789",
                "Description");


        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(brand)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        Brand brand = new Brand(
                1L,
                "Name",
                "123457891234578912345789123457891234578912345789123457891234578912345789123457891234578912345789");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(brand)
        );

        assertEquals(CategoryExceptionMessage.TOO_LONG_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Brand brand = new Brand(1L, null, "Description");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(brand)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Brand brand = new Brand(1L, "", "Description");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(brand)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_NAME, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsNull() {
        Brand brand = new Brand(1L, "Name", null);

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(brand)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsEmpty() {
        Brand brand = new Brand(1L, "Name", "");

        CategoryNotValidFieldException exception = assertThrows(
                CategoryNotValidFieldException.class, () -> categoryCreateUseCase.createCategory(brand)
        );

        assertEquals(CategoryExceptionMessage.EMPTY_DESCRIPTION, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExists() {
        Brand brand = new Brand(1L, "ExistingCategory", "Description");

        when(categoryPersistencePort.isBradPresentByName("ExistingCategory")).thenReturn(true);

        CategoryAlreadyExistException exception = assertThrows(
                CategoryAlreadyExistException.class, () -> categoryCreateUseCase.createCategory(brand)
        );

        verify(categoryPersistencePort, times(1)).isBradPresentByName("ExistingCategory");
        assertEquals(CategoryExceptionMessage.ALREADY_EXIST_CATEGORY, exception.getMessage());
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        Brand brand = new Brand(1L, "Name", "Description");

        assertDoesNotThrow(() -> categoryCreateUseCase.createCategory(brand));

        verify(categoryPersistencePort).createCategory(brand);
    }
}