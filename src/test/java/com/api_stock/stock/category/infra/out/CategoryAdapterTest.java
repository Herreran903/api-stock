package com.api_stock.stock.category.infra.out;

import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.infra.exception.ex.CategoryAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryAdapterTest {

    @Mock
    private ICategoryRepository repository;

    @Mock
    private ICategoryMapper mapper;

    @InjectMocks
    private CategoryAdapter categoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenCategoryAlreadyExists() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        CategoryEntity categoryEntity = new CategoryEntity();

        when(repository.findByName("Electronics")).thenReturn(Optional.of(categoryEntity));

        assertThrows(CategoryAlreadyExistException.class, () -> categoryAdapter.createCategory(category));
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        CategoryEntity categoryEntity = new CategoryEntity();

        when(repository.findByName("Electronics")).thenReturn(Optional.empty());
        when(mapper.toEntity(category)).thenReturn(categoryEntity);

        categoryAdapter.createCategory(category);

        verify(repository).save(categoryEntity);
    }

}
