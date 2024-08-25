package com.api_stock.stock.infra.category.in;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.app.category.handler.ICategoryHandler;
import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.domain.category.exception.ex.CategoryNotValidParameterException;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

    @ParameterizedTest
    @ValueSource(strings = {"null", "", "missing"})
    void shouldReturnBadRequestForInvalidCategoryName(String name) throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = CategoryExceptionMessage.EMPTY_NAME;

        String requestBody;
        if ("missing".equals(name)) {
            requestBody = "{\"description\":\"Devices and gadgets\"}";
        } else if ("null".equals(name)) {
            requestBody = "{\"name\":null,\"description\":\"Devices and gadgets\"}";
        } else { // "" (empty string case)
            requestBody = "{\"name\":\"\",\"description\":\"Devices and gadgets\"}";
        }

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("name"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @ParameterizedTest
    @ValueSource(strings = {"null", "", "missing"})
    void shouldReturnBadRequestForInvalidCategoryDescription(String description) throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = CategoryExceptionMessage.EMPTY_DESCRIPTION;

        String requestBody;
        if ("missing".equals(description)) {
            requestBody = "{\"name\":\"Electronics\"}";
        } else if ("null".equals(description)) {
            requestBody = "{\"name\":\"Electronics\",\"description\":null}";
        } else { // "" (empty string case)
            requestBody = "{\"name\":\"Electronics\",\"description\":\"\"}";
        }

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("description"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestIfJsonNoValid() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_JSON;

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":,}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionBadTypes() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_JSON;
        String expectedErrorMessage = String.format(GlobalExceptionMessage.INVALID_TYPE_PARAM, "description", "String");

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\", \"description\":[]}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("description"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryNameBadTypes() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_JSON;
        String expectedErrorMessage = String.format(GlobalExceptionMessage.INVALID_TYPE_PARAM, "name", "String");

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":[], \"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("name"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
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
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = CategoryExceptionMessage.INVALID_SORT_DIRECTION;

        when(categoryHandler.getCategoriesByPage(0,10,"INVALID")).
                thenThrow(new CategoryNotValidParameterException(expectedMessage));

        mvc.perform(get("/categories/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "INVALID"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("sortDirection"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestIfPageIsNegative() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = CategoryExceptionMessage.NO_NEGATIVE_PAGE;

        when(categoryHandler.getCategoriesByPage(-1,10,"ASC")).
                thenThrow(new CategoryNotValidParameterException(expectedMessage));

        mvc.perform(get("/categories/")
                        .param("page", "-1")
                        .param("size", "10")
                        .param("sortDirection", "ASC"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("page"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));

    }

    @Test
    void shouldReturnBadRequestIfSizeIsZeroOrNegative() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = CategoryExceptionMessage.GREATER_ZERO_SIZE;

        when(categoryHandler.getCategoriesByPage(0,0,"ASC")).
                thenThrow(new CategoryNotValidParameterException(expectedMessage));

        mvc.perform(get("/categories/")
                        .param("page", "0")
                        .param("size", "0")
                        .param("sortDirection", "ASC"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("size"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
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