package com.api_stock.stock.brand.infra.out;

import com.api_stock.stock.brand.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrandAdapterTest {

    @Mock
    private IBrandRepository repository;

    @Mock
    private IBrandMapper mapper;

    @InjectMocks
    private BrandAdapter brandAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateBrandSuccessfully() {
        Brand brand = new Brand(1L, "Nike", "High quality clothing");
        BrandEntity brandEntity = new BrandEntity();

        when(repository.findByName("Nike")).thenReturn(Optional.empty());
        when(mapper.toEntity(brand)).thenReturn(brandEntity);

        brandAdapter.createBrand(brand);

        verify(repository).save(brandEntity);
    }

}