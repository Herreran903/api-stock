package com.api_stock.stock.category.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void shouldCreateCategorySuccessfully() {
        Category category = new Category(1L, "Name", "Description");

        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("Name", category.getName());
        assertEquals("Description", category.getDescription());
    }
}
