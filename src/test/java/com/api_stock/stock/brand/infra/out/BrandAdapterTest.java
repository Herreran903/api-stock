package com.api_stock.stock.brand.infra.out;

import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.model.BrandPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void shouldReturnBrandsPage() {
        //Arrange
        int page = 0;
        int size = 10;
        String orderDirection = "ASC";

        List<BrandEntity> entities = List.of(
                new BrandEntity(1L, "name","desc"),
                new BrandEntity(2L, "name1", "desc"));
        Page<BrandEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), entities.size());
        when(repository.findAll(any(Pageable.class))).thenReturn(pageResult);

        List<Brand> brands = List.of(
                new Brand(1L, "name","desc"),
                new Brand(2L, "name1", "desc"));

        when(mapper.toBrandsList(entities)).thenReturn(brands);

        //Act
        BrandPage<Brand> result = brandAdapter.getBrandsByPage(page, size, orderDirection);

        //Assert
        assertEquals(entities.size(), result.getData().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(entities.size(), result.getTotalElements());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertFalse(result.isHasNext());
        assertFalse(result.isHasPrevious());

        verify(repository).findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        verify(mapper).toBrandsList(entities);
    }

}