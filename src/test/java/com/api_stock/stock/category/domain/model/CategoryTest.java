package com.api_stock.stock.category.domain.model;

import com.api_stock.stock.category.domain.exception.ex.EmptyFieldException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(EmptyFieldException.class, () ->
                new Category(1L, null, "Description"));
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(EmptyFieldException.class, () ->
                new Category(1L, "", "Description"));
    }

    @Test void shouldThrowExceptionWhenDescriptionIsNull() {
        assertThrows(EmptyFieldException.class, () ->
                new Category(1L, "Description", null));
    }

    @Test void shouldThrowExceptionWhenDescriptionIsEmpty() {
        assertThrows(EmptyFieldException.class, () ->
                new Category(1L, "Description", ""));
    }

    @Test void shouldCreateCategorySuccessfully() {
        Category category = new Category(1L, "Name", "Description");

        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("Name", category.getName());
        assertEquals("Description", category.getDescription());
    }
}
