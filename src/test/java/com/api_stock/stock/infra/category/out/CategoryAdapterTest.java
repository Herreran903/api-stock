package com.api_stock.stock.infra.category.out;

import com.api_stock.stock.domain.category.model.Category;
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
        Category category = new Category(1L, "Electronics", "Devices and gadgets");
        CategoryEntity categoryEntity = new CategoryEntity();

        when(repository.findByName("Electronics")).thenReturn(Optional.empty());
        when(mapper.toEntity(category)).thenReturn(categoryEntity);

        categoryAdapter.createCategory(category);

        verify(repository).save(categoryEntity);
    }

    @Test
    void shouldReturnCategoriesPage() {
        //Arrange
        int page = 0;
        int size = 10;
        String orderDirection = "ASC";

        List<CategoryEntity> entities = List.of(
                new CategoryEntity(1L, "Electronics","Devices and gadgets"),
                new CategoryEntity(2L, "Electronics1", "Devices and gadgets"));
        Page<CategoryEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), entities.size());
        when(repository.findAll(any(Pageable.class))).thenReturn(pageResult);

        List<Category> categories = List.of(
                new Category(1L, "Electronics","Devices and gadgets"),
                new Category(2L, "Electronics1", "Devices and gadgets"));

        when(mapper.toCategoriesList(entities)).thenReturn(categories);

        //Act
        PageData<Category> result = categoryAdapter.getCategoriesByPage(page, size, orderDirection);

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

    @Test
    void testIsCategoryPresentByNameWhenNameExists() {
        String name = "ExistingName";
        when(repository.findByName(name)).thenReturn(Optional.of(new CategoryEntity(1L, name, "Description")));

        Boolean result = categoryAdapter.isCategoryPresentByName(name);

        assertTrue(result);
    }

    @Test
    void testIsCategoryPresentByNameWhenNameDoesNotExist() {
        String name = "NonExistingName";
        when(repository.findByName(name)).thenReturn(Optional.empty());

        Boolean result = categoryAdapter.isCategoryPresentByName(name);

        assertFalse(result);
    }
}
