package com.api_stock.stock.category.domain.usecase;

import com.api_stock.stock.category.domain.exception.ex.EmptyFieldException;
import com.api_stock.stock.category.domain.exception.ex.MaxLengthExceededException;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

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


        assertThrows(
                MaxLengthExceededException.class, () -> categoryCreateUseCase.createCategory(category)
        );
    }

    @Test
    void shouldThrowExceptionWhenDescriptionExceedsMaxLength() {
        Category category = new Category(
                1L,
                "Name",
                "123457891234578912345789123457891234578912345789123457891234578912345789123457891234578912345789");

        assertThrows(
                MaxLengthExceededException.class, () -> categoryCreateUseCase.createCategory(category)
        );
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        Category category = new Category(1L, null, "Description");

        assertThrows(
                EmptyFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        Category category = new Category(1L, "", "Description");

        assertThrows(
                EmptyFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );
    }

    @Test void shouldThrowExceptionWhenDescriptionIsNull() {
        Category category = new Category(1L, "Name", null);

        assertThrows(
                EmptyFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );
    }

    @Test void shouldThrowExceptionWhenDescriptionIsEmpty() {
        Category category = new Category(1L, "Name", "");

        assertThrows(
                EmptyFieldException.class, () -> categoryCreateUseCase.createCategory(category)
        );
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        Category category = new Category(1L, "Name", "Description");

        assertDoesNotThrow(() -> categoryCreateUseCase.createCategory(category));

        verify(categoryPersistencePort).createCategory(category);
    }
}