package com.api_stock.stock.category.app.handler;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.dto.CategoryResponse;
import com.api_stock.stock.category.app.exception.AppExceptionMessage;
import com.api_stock.stock.category.app.exception.ex.InvalidParameterException;
import com.api_stock.stock.category.app.mapper.ICategoryRequestMapper;
import com.api_stock.stock.category.app.mapper.ICategoryResponseMapper;
import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.api.IGetCategoriesByPageServicePort;
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
    private IGetCategoriesByPageServicePort getCategoriesByPageService;

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
    void shouldThrowInvalidParameterExceptionWhenSortDirectionIsInvalid() {
        String invalidSortDirection = "INVALID";

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> categoryHandler.getCategoriesByPage(0, 10, invalidSortDirection)
        );

        assertEquals(AppExceptionMessage.INVALID_SORT_DIRECTION.getMessage(), exception.getMessage());
        verifyNoInteractions(getCategoriesByPageService);
    }

    @Test
    void shouldThrowInvalidParameterExceptionWhenPageIsNegative() {
        int negativePage = -1;

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> categoryHandler.getCategoriesByPage(negativePage, 10, "ASC")
        );

        assertEquals(AppExceptionMessage.NO_NEGATIVE_PAGE.getMessage(), exception.getMessage());
        verifyNoInteractions(getCategoriesByPageService);
    }

    @Test
    void shouldThrowInvalidParameterExceptionWhenSizeIsLessThanOrEqualToZero() {
        int invalidSize = 0;

        InvalidParameterException exception = assertThrows(
                InvalidParameterException.class,
                () -> categoryHandler.getCategoriesByPage(0, invalidSize, "ASC")
        );

        assertEquals(AppExceptionMessage.SIZE_GREATER_ZERO.getMessage(), exception.getMessage());
        verifyNoInteractions(getCategoriesByPageService);
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
