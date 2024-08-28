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
import static org.mockito.Mockito.*;

class CategoryAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryMapper categoryMapper;

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

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.empty());
        when(categoryMapper.toEntity(category)).thenReturn(categoryEntity);

        categoryAdapter.createCategory(category);

        verify(categoryRepository).save(categoryEntity);
    }

    @Test
    void shouldReturnCategoriesPage() {
        //Arrange
        Integer page = 0;
        Integer size = 10;
        String orderDirection = "ASC";

        List<CategoryEntity> entities = List.of(
                new CategoryEntity(1L, "Electronics","Devices and gadgets"),
                new CategoryEntity(2L, "Electronics1", "Devices and gadgets"));
        Page<CategoryEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), entities.size());
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

        List<Category> categories = List.of(
                new Category(1L, "Electronics","Devices and gadgets"),
                new Category(2L, "Electronics1", "Devices and gadgets"));

        when(categoryMapper.toCategoriesList(entities)).thenReturn(categories);

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

        verify(categoryRepository).findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        verify(categoryMapper).toCategoriesList(entities);
    }

    @Test
    void testIsCategoryPresentByNameWhenNameExists() {
        String name = "Electronics";
        when(categoryRepository.findByName(name)).thenReturn(Optional.of(new CategoryEntity(1L, name, "Devices and gadgets")));

        Boolean result = categoryAdapter.isCategoryPresentByName(name);

        assertTrue(result);
    }

    @Test
    void testIsCategoryPresentByNameWhenNameDoesNotExist() {
        String name = "Electronics";
        when(categoryRepository.findByName(name)).thenReturn(Optional.empty());

        Boolean result = categoryAdapter.isCategoryPresentByName(name);

        assertFalse(result);
    }

    @Test
    void shouldReturnCategoriesWhenIdsAreValid() {
        List<Long> ids = List.of(1L, 2L);
        List<CategoryEntity> categoryEntities = List.of(
                new CategoryEntity(1L, "Electronics", "Devices and gadgets"),
                new CategoryEntity(2L, "Books", "Different kinds of books")
        );
        List<Category> expectedCategories = List.of(
                new Category(1L, "Electronics", "Devices and gadgets"),
                new Category(2L, "Books", "Different kinds of books")
        );

        when(categoryRepository.findAllById(ids)).thenReturn(categoryEntities);
        when(categoryMapper.toCategoriesList(categoryEntities)).thenReturn(expectedCategories);

        List<Category> result = categoryAdapter.getCategoriesByIds(ids);

        assertEquals(expectedCategories, result);
        verify(categoryRepository, times(1)).findAllById(ids);
        verify(categoryMapper, times(1)).toCategoriesList(categoryEntities);
    }
}
