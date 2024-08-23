package com.api_stock.stock.category.app.handler;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.dto.CategoryResponse;
import com.api_stock.stock.category.app.mapper.ICategoryRequestMapper;
import com.api_stock.stock.category.app.mapper.ICategoryResponseMapper;
import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.api.ICategoriesGetByPageServicePort;
import com.api_stock.stock.category.domain.model.Category;
import com.api_stock.stock.category.domain.model.CategoryPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryHandlerTest {

    @Mock
    private ICategoryCreateServicePort categoryCreateService;

    @Mock
    private ICategoriesGetByPageServicePort getCategoriesByPageService;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @Mock
    private ICategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryHandler categoryHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCallCreateCategoryOnCategoryCreateService() {
        CategoryRequest categoryRequest = new CategoryRequest("Electronics", "Devices and gadgets");
        Category category = new Category(1L, "Electronics", "Devices and gadgets");

        when(categoryRequestMapper.toCategory(categoryRequest)).thenReturn(category);

        categoryHandler.createCategory(categoryRequest);

        verify(categoryCreateService).createCategory(category);
    }

    @Test
    void shouldReturnCategoryPageWhenParametersAreValid() {
        int page = 0;
        int size = 10;
        String sortDirection = "ASC";

        CategoryPage<Category> mockCategoryPage = mock(CategoryPage.class);
        CategoryPage<CategoryResponse> mockResponsePage = mock(CategoryPage.class);

        when(getCategoriesByPageService.getCategoriesByPage(page, size, sortDirection)).thenReturn(mockCategoryPage);
        when(categoryResponseMapper.toPageResponse(mockCategoryPage)).thenReturn(mockResponsePage);

        CategoryPage<CategoryResponse> result = categoryHandler.getCategoriesByPage(page, size, sortDirection);

        assertNotNull(result);
        assertEquals(mockResponsePage, result);

        verify(getCategoriesByPageService).getCategoriesByPage(page, size, sortDirection);
        verify(categoryResponseMapper).toPageResponse(mockCategoryPage);
    }
}
