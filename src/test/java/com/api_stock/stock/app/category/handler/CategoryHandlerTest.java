package com.api_stock.stock.app.category.handler;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.app.category.dto.CategoryResponse;
import com.api_stock.stock.app.category.mapper.ICategoryRequestMapper;
import com.api_stock.stock.app.category.mapper.ICategoryResponseMapper;
import com.api_stock.stock.domain.category.api.ICategoryServicePort;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryHandlerTest {

    @Mock
    private ICategoryServicePort categoryServicePort;

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

        categoryHandler.createBrand(categoryRequest);

        verify(categoryServicePort, times(1)).createCategory(category);
    }

    @Test
    void shouldReturnCategoryPageWhenParametersAreValid() {
        Integer page = 0;
        Integer size = 10;
        String sortDirection = "ASC";

        PageData<Category> mockCategoryPage = mock(PageData.class);
        PageData<CategoryResponse> mockResponsePage = mock(PageData.class);

        when(categoryServicePort.getCategoriesByPage(page, size, sortDirection)).thenReturn(mockCategoryPage);
        when(categoryResponseMapper.toPageResponse(mockCategoryPage)).thenReturn(mockResponsePage);

        PageData<CategoryResponse> result = categoryHandler.getCategoriesByPage(page, size, sortDirection);

        assertNotNull(result);
        assertEquals(mockResponsePage, result);

        verify(categoryServicePort).getCategoriesByPage(page, size, sortDirection);
        verify(categoryResponseMapper).toPageResponse(mockCategoryPage);
    }
}
