package com.api_stock.stock.infra.category.in;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.app.category.handler.ICategoryHandler;
import com.api_stock.stock.domain.category.exception.CategoryExceptionMessage;
import com.api_stock.stock.domain.category.exception.ex.CategoryAlreadyExistException;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
import com.api_stock.stock.infra.security.jwt.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc(addFilters=false)
class CategoryControllerTest {

    @MockBean
    private JwtService jwtService;

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

        mvc.perform(post("/category/create")
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

        mvc.perform(post("/category/create")
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

        mvc.perform(post("/category/create")
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

        mvc.perform(post("/category/create")
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

        mvc.perform(post("/category/create")
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
        mvc.perform(post("/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(categoryHandler, times(1)).createBrand(any(CategoryRequest.class));
    }

    @Test
    void shouldReturnConflictWhenCategoryNameAlreadyExists() throws Exception {
        String expectedMessage = CategoryExceptionMessage.ALREADY_EXIST_CATEGORY;

        doThrow(new CategoryAlreadyExistException(expectedMessage)).when(categoryHandler).createBrand(any(CategoryRequest.class));

        mvc.perform(post("/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfSortDirectionIsInvalid() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = GlobalExceptionMessage.INVALID_ORDER;

        mvc.perform(get("/category/fetch")
                        .param("page", String.valueOf(GlobalConstants.MIN_PAGE_NUMBER))
                        .param("size", String.valueOf(GlobalConstants.MIN_PAGE_SIZE))
                        .param("order", "INVALID"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("order"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestIfPageIsNegative() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = GlobalExceptionMessage.NO_NEGATIVE_PAGE;

        mvc.perform(get("/category/fetch")
                        .param("page", "-1")
                        .param("size", String.valueOf(GlobalConstants.MIN_PAGE_SIZE))
                        .param("order", GlobalConstants.ASC))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("page"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));

    }

    @Test
    void shouldReturnBadRequestIfSizeIsZeroOrNegative() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = GlobalExceptionMessage.GREATER_ZERO_SIZE;

        mvc.perform(get("/category/fetch")
                        .param("page", String.valueOf(GlobalConstants.MIN_PAGE_NUMBER))
                        .param("size", "0")
                        .param("order", GlobalConstants.ASC))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("size"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnOkWhenParametersAreValid() throws Exception {
        mvc.perform(get("/category/fetch")
                        .param("page", String.valueOf(GlobalConstants.MIN_PAGE_NUMBER))
                        .param("size", String.valueOf(GlobalConstants.MIN_PAGE_SIZE))
                        .param("order", GlobalConstants.ASC))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryHandler, times(1)).getCategoriesByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                GlobalConstants.ASC);
    }
}