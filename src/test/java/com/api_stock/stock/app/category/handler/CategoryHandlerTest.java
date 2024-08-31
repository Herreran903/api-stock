package com.api_stock.stock.app.category.handler;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.app.category.dto.CategoryResponse;
import com.api_stock.stock.app.category.mapper.ICategoryRequestMapper;
import com.api_stock.stock.app.category.mapper.ICategoryResponseMapper;
import com.api_stock.stock.domain.category.api.ICategoryServicePort;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.api_stock.stock.utils.TestConstants.*;
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

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category(VALID_CATEGORY_ID, VALID_CATEGORY_NAME, VALID_CATEGORY_DESCRIPTION);
    }

    @Test
    void shouldCallCreateCategoryOnCategoryCreateService() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(VALID_CATEGORY_NAME);
        categoryRequest.setDescription(VALID_CATEGORY_DESCRIPTION);

        when(categoryRequestMapper.toCategory(categoryRequest)).thenReturn(category);

        categoryHandler.createBrand(categoryRequest);

        verify(categoryServicePort, times(1)).createCategory(category);
    }

    @Test
    void shouldReturnCategoryPageWhenParametersAreValid() {
        int page = GlobalConstants.MIN_PAGE_NUMBER;
        int size = Integer.parseInt(GlobalConstants.DEFAULT_PAGE_SIZE);
        String sortDirection = GlobalConstants.ASC;

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
