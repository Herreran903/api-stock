package com.api_stock.stock.category.infra.in;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.handler.ICategoryHandler;
import com.api_stock.stock.category.domain.exception.CategoryExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidFieldException;
import com.api_stock.stock.category.domain.exception.ex.CategoryNotValidParameterException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ICategoryHandler categoryHandler;

    @Test
    void shouldReturnBadRequestIfCategoryNameIsNull() throws Exception {
        String expectedMessage = CategoryExceptionMessage.EMPTY_NAME;

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName() == null) {
                throw new CategoryNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createBrand(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":null,\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryNameIsEmpty() throws Exception {
        String expectedMessage = CategoryExceptionMessage.EMPTY_NAME;

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName().isEmpty()) {
                throw new CategoryNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createBrand(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryNameIsMissing() throws Exception {
        String expectedMessage = CategoryExceptionMessage.EMPTY_NAME;

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName() == null || request.getName().isEmpty()) {
                throw new CategoryNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createBrand(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));

    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsNull() throws Exception {
        String expectedMessage = CategoryExceptionMessage.EMPTY_DESCRIPTION;

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription() == null) {
                throw new CategoryNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createBrand(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":null}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsEmpty() throws Exception {
        String expectedMessage = CategoryExceptionMessage.EMPTY_DESCRIPTION;

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription().isEmpty()) {
                throw new CategoryNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createBrand(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsMissing() throws Exception {
        String expectedMessage = CategoryExceptionMessage.EMPTY_DESCRIPTION;

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription() == null || request.getDescription().isEmpty()) {
                throw new CategoryNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createBrand(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnCreatedWhenCategoryIsSuccessfullyCreated() throws Exception {
        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(categoryHandler).createBrand(any(CategoryRequest.class));
    }

    @Test
    void shouldReturnConflictWhenCategoryNameAlreadyExists() throws Exception {
        String expectedMessage = CategoryExceptionMessage.ALREADY_EXIST_CATEGORY;

        doThrow(new CategoryAlreadyExistException(expectedMessage)).when(categoryHandler).createBrand(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfSortDirectionIsInvalid() throws Exception {
        String expectedMessage = CategoryExceptionMessage.INVALID_SORT_DIRECTION;

        when(categoryHandler.getCategoriesByPage(0,10,"INVALID")).
                thenThrow(new CategoryNotValidParameterException(expectedMessage));

        mvc.perform(get("/categories/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "INVALID"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfPageIsNegative() throws Exception {
        String expectedMessage = CategoryExceptionMessage.NO_NEGATIVE_PAGE;

        when(categoryHandler.getCategoriesByPage(-1,10,"ASC")).
                thenThrow(new CategoryNotValidParameterException(expectedMessage));

        mvc.perform(get("/categories/")
                        .param("page", "-1")
                        .param("size", "10")
                        .param("sortDirection", "ASC"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));

    }

    @Test
    void shouldReturnBadRequestIfSizeIsZeroOrNegative() throws Exception {
        String expectedMessage = CategoryExceptionMessage.GREATER_ZERO_SIZE;

        when(categoryHandler.getCategoriesByPage(0,0,"ASC")).
                thenThrow(new CategoryNotValidParameterException(expectedMessage));

        mvc.perform(get("/categories/")
                        .param("page", "0")
                        .param("size", "0")
                        .param("sortDirection", "ASC"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnOkWhenParametersAreValid() throws Exception {
        mvc.perform(get("/categories/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "ASC"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryHandler, times(1)).getCategoriesByPage(0, 10, "ASC");
    }
}