package com.api_stock.stock.infra.brand.out;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.page.PageData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BrandAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private IBrandMapper brandMapper;

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

        when(brandRepository.findByName("Nike")).thenReturn(Optional.empty());
        when(brandMapper.toEntity(brand)).thenReturn(brandEntity);

        brandAdapter.createBrand(brand);

        verify(brandRepository).save(brandEntity);
    }

    @Test
    void shouldReturnBrandsPage() {
        //Arrange
        Integer page = 0;
        Integer size = 10;
        String orderDirection = "ASC";

        List<BrandEntity> entities = List.of(
                new BrandEntity(1L, "name","desc"),
                new BrandEntity(2L, "name1", "desc"));
        Page<BrandEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), entities.size());
        when(brandRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

        List<Brand> brands = List.of(
                new Brand(1L, "name","desc"),
                new Brand(2L, "name1", "desc"));

        when(brandMapper.toBrandsList(entities)).thenReturn(brands);

        //Act
        PageData<Brand> result = brandAdapter.getBrandsByPage(page, size, orderDirection);

        //Assert
        assertEquals(entities.size(), result.getData().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(entities.size(), result.getTotalElements());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertFalse(result.isHasNext());
        assertFalse(result.isHasPrevious());

        verify(brandRepository).findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        verify(brandMapper).toBrandsList(entities);
    }

    @Test
    void testIsBrandPresentByNameWhenNameExists() {
        String name = "ExistingName";
        when(brandRepository.findByName(name)).thenReturn(Optional.of(new BrandEntity(1L, name, "Description")));

        Boolean result = brandAdapter.isBrandPresentByName(name);

        assertTrue(result);
    }

    @Test
    void testIsBrandPresentByNameWhenNameDoesNotExist() {
        String name = "NonExistingName";
        when(brandRepository.findByName(name)).thenReturn(Optional.empty());

        Boolean result = brandAdapter.isBrandPresentByName(name);

        assertFalse(result);
    }

    @Test
    void getBrandById_ShouldReturnBrand_WhenBrandExists() {
        Long brandId = 1L;
        Brand expectedBrand = new Brand(brandId, "Nike", "High quality clothing");
        BrandEntity brandEntity = new BrandEntity(brandId, "Nike", "High quality clothing");

        when(brandRepository.findById(brandId)).thenReturn(Optional.of(brandEntity));
        when(brandMapper.toBrand(brandEntity)).thenReturn(expectedBrand);

        Optional<Brand> actualBrand = brandAdapter.getBrandById(brandId);

        assertTrue(actualBrand.isPresent());
        assertEquals(expectedBrand, actualBrand.get());
    }

    @Test
    void getBrandById_ShouldReturnEmpty_WhenBrandDoesNotExist() {
        Long brandId = 1L;
        when(brandRepository.findById(brandId)).thenReturn(Optional.empty());

        Optional<Brand> actualBrand = brandAdapter.getBrandById(brandId);

        assertTrue(actualBrand.isEmpty());
    }
}