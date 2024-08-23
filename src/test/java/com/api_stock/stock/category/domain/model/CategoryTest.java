package com.api_stock.stock.category.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void shouldCreateCategorySuccessfully() {
        Brand brand = new Brand(1L, "Name", "Description");

        assertNotNull(brand);
        assertEquals(1L, brand.getId());
        assertEquals("Name", brand.getName());
        assertEquals("Description", brand.getDescription());
    }
}
