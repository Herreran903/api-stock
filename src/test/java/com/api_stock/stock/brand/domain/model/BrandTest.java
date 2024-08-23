package com.api_stock.stock.brand.domain.model;

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
}