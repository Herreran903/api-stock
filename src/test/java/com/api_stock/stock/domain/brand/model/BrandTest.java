package com.api_stock.stock.domain.brand.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandTest {

    @Test
    void shouldCreateBrandSuccessfully() {
        Brand brand = new Brand(1L, "Nike", "High quality clothing");

        assertNotNull(brand);
        assertEquals(1L, brand.getId());
        assertEquals("Nike", brand.getName());
        assertEquals("High quality clothing", brand.getDescription());
    }

    @Test
    void shouldUpdateBrandSuccessfully() {
        Brand brand = new Brand(1L, "Nike", "High quality clothing");

        brand.setId(2L);
        brand.setName("High quality clothing");
        brand.setDescription("High quality clothing 2");

        assertNotNull(brand);
        assertEquals(2L, brand.getId());
        assertEquals("High quality clothing", brand.getName());
        assertEquals("High quality clothing 2", brand.getDescription());
    }
}