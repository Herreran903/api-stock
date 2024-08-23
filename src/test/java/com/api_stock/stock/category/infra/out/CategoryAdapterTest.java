package com.api_stock.stock.category.infra.out;

import com.api_stock.stock.category.domain.model.Brand;
import com.api_stock.stock.category.domain.model.CategoryPage;
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

class CategoryAdapterTest {

    @Mock
    private ICategoryRepository repository;

    @Mock
    private ICategoryMapper mapper;

    @InjectMocks
    private CategoryAdapter categoryAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        Brand brand = new Brand(1L, "Electronics", "Devices and gadgets");
        CategoryEntity categoryEntity = new CategoryEntity();

        when(repository.findByName("Electronics")).thenReturn(Optional.empty());
        when(mapper.toEntity(brand)).thenReturn(categoryEntity);

        categoryAdapter.createCategory(brand);

        verify(repository).save(categoryEntity);
    }

    @Test
    void shouldReturnCategoriesPage() {
        //Arrange
        int page = 0;
        int size = 10;
        String orderDirection = "ASC";

        List<CategoryEntity> entities = List.of(
                new CategoryEntity(1L, "name","desc"),
                new CategoryEntity(2L, "name1", "desc"));
        Page<CategoryEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), entities.size());
        when(repository.findAll(any(Pageable.class))).thenReturn(pageResult);

        List<Brand> categories = List.of(
                new Brand(1L, "name","desc"),
                new Brand(2L, "name1", "desc"));

        when(mapper.toCategoriesList(entities)).thenReturn(categories);

        //Act
        CategoryPage<Brand> result = categoryAdapter.getCategoriesByPage(page, size, orderDirection);

        //Assert
        assertEquals(entities.size(), result.getData().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(entities.size(), result.getTotalElements());
        assertTrue(result.isFirst());
        assertTrue(result.isLast());
        assertFalse(result.isHasNext());
        assertFalse(result.isHasPrevious());

        verify(repository).findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        verify(mapper).toCategoriesList(entities);
    }
}
