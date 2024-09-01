package com.api_stock.stock.infra.brand.out;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static com.api_stock.stock.utils.TestConstants.*;
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

    private Brand brand;
    private BrandEntity brandEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        brand = new Brand(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
        brandEntity = new BrandEntity(VALID_BRAND_ID, VALID_BRAND_NAME, VALID_BRAND_DESCRIPTION);
    }

    @Test
    void shouldCreateBrandSuccessfully() {
        when(brandRepository.findByName(VALID_BRAND_NAME)).thenReturn(Optional.empty());
        when(brandMapper.toEntity(brand)).thenReturn(brandEntity);

        brandAdapter.createBrand(brand);

        verify(brandRepository).save(brandEntity);
    }

    @Test
    void shouldReturnBrandsPage() {
        //Arrange
        int page = GlobalConstants.MIN_PAGE_NUMBER;
        int size = GlobalConstants.MIN_PAGE_SIZE;
        String orderDirection = GlobalConstants.ASC;
        List<BrandEntity> entities = List.of(brandEntity ,brandEntity);
        Page<BrandEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), entities.size());
        when(brandRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

        List<Brand> brands = List.of(brand, brand);

        when(brandMapper.toBrandsList(entities)).thenReturn(brands);

        //Act
        PageData<Brand> result = brandAdapter.getBrandsByPage(page, size, orderDirection);

        //Assert
        assertEquals(entities.size(), result.getData().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(entities.size(), result.getTotalElements());
        assertTrue(result.isFirst());
        assertFalse(result.isLast());
        assertTrue(result.isHasNext());
        assertFalse(result.isHasPrevious());

        verify(brandRepository).findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        verify(brandMapper).toBrandsList(entities);
    }

    @Test
    void testIsBrandPresentByNameWhenNameExists() {
        when(brandRepository.findByName(VALID_BRAND_NAME)).thenReturn(Optional.of(brandEntity));

        Boolean result = brandAdapter.isBrandPresentByName(VALID_BRAND_NAME);

        assertTrue(result);
    }

    @Test
    void testIsBrandPresentByNameWhenNameDoesNotExist() {
        when(brandRepository.findByName(VALID_BRAND_NAME)).thenReturn(Optional.empty());

        Boolean result = brandAdapter.isBrandPresentByName(VALID_BRAND_NAME);

        assertFalse(result);
    }

    @Test
    void getBrandByIdShouldReturnBrandWhenBrandExists() {
        when(brandRepository.findById(VALID_BRAND_ID)).thenReturn(Optional.of(brandEntity));
        when(brandMapper.toBrand(brandEntity)).thenReturn(brand);

        Optional<Brand> actualBrand = brandAdapter.getBrandById(VALID_BRAND_ID);

        assertTrue(actualBrand.isPresent());
        assertEquals(brand, actualBrand.get());
    }

    @Test
    void getBrandByIdShouldReturnEmptyWhenBrandDoesNotExist() {
        when(brandRepository.findById(VALID_BRAND_ID)).thenReturn(Optional.empty());

        Optional<Brand> actualBrand = brandAdapter.getBrandById(VALID_BRAND_ID);

        assertTrue(actualBrand.isEmpty());
    }
}