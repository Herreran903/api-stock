package com.api_stock.stock.domain.category.model;

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

    @Test
    void shouldUpdateCategorySuccessfully() {
        Category category = new Category(1L, "Name", "Description");
        category.setId(2L);
        category.setName("New Name");
        category.setDescription("New Description");

        assertNotNull(category);
        assertEquals(2L, category.getId());
        assertEquals("New Name", category.getName());
        assertEquals("New Description", category.getDescription());
    }
}
