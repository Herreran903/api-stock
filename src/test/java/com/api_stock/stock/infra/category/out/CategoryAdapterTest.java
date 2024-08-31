package com.api_stock.stock.infra.category.out;

import com.api_stock.stock.domain.category.model.Category;
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
import static org.mockito.Mockito.*;

class CategoryAdapterTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryMapper categoryMapper;

    @InjectMocks
    private CategoryAdapter categoryAdapter;

    private Category category;
    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
        categoryEntity = new CategoryEntity(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }

    @Test
    void shouldCreateCategorySuccessfully() {
        when(categoryRepository.findByName(VALID_CATEGORY_NAME)).thenReturn(Optional.empty());
        when(categoryMapper.toEntity(category)).thenReturn(categoryEntity);

        categoryAdapter.createCategory(category);

        verify(categoryRepository).save(categoryEntity);
    }

    @Test
    void shouldReturnCategoriesPage() {
        //Arrange
        int page = GlobalConstants.MIN_PAGE_NUMBER;
        int size = GlobalConstants.MIN_PAGE_SIZE;
        String orderDirection = GlobalConstants.ASC;

        List<CategoryEntity> entities = List.of(categoryEntity, categoryEntity);
        Page<CategoryEntity> pageResult = new PageImpl<>(entities, PageRequest.of(page, size), entities.size());
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(pageResult);

        List<Category> categories = List.of(category, category);

        when(categoryMapper.toCategoriesList(entities)).thenReturn(categories);

        //Act
        PageData<Category> result = categoryAdapter.getCategoriesByPage(page, size, orderDirection);

        //Assert
        assertEquals(entities.size(), result.getData().size());
        assertEquals(page, result.getPageNumber());
        assertEquals(entities.size(), result.getTotalElements());
        assertTrue(result.isFirst());
        assertFalse(result.isLast());
        assertTrue(result.isHasNext());
        assertFalse(result.isHasPrevious());

        verify(categoryRepository).findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name")));
        verify(categoryMapper).toCategoriesList(entities);
    }

    @Test
    void testIsCategoryPresentByNameWhenNameExists() {
        when(categoryRepository.findByName(VALID_CATEGORY_NAME))
                .thenReturn(Optional.of(categoryEntity));

        Boolean result = categoryAdapter.isCategoryPresentByName(VALID_CATEGORY_NAME);

        assertTrue(result);
    }

    @Test
    void testIsCategoryPresentByNameWhenNameDoesNotExist() {
        when(categoryRepository.findByName(VALID_CATEGORY_NAME)).thenReturn(Optional.empty());

        Boolean result = categoryAdapter.isCategoryPresentByName(VALID_CATEGORY_NAME);

        assertFalse(result);
    }

    @Test
    void shouldReturnCategoriesWhenIdsAreValid() {
        List<Long> ids = List.of(VALID_CATEGORY_ID);
        List<CategoryEntity> categoryEntities = List.of(categoryEntity);
        List<Category> expectedCategories = List.of(category);

        when(categoryRepository.findAllById(ids)).thenReturn(categoryEntities);
        when(categoryMapper.toCategoriesList(categoryEntities)).thenReturn(expectedCategories);

        List<Category> result = categoryAdapter.getCategoriesByIds(ids);

        assertEquals(expectedCategories, result);
        verify(categoryRepository, times(1)).findAllById(ids);
        verify(categoryMapper, times(1)).toCategoriesList(categoryEntities);
    }
}
