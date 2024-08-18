package com.api_stock.stock.category.app.handler;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.mapper.ICategoryRequestMapper;
import com.api_stock.stock.category.domain.api.ICategoryCreateServicePort;
import com.api_stock.stock.category.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryHandlerTest {

    @Mock
    private ICategoryCreateServicePort categoryCreateService;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

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
}
