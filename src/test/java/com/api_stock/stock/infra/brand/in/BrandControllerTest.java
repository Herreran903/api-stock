package com.api_stock.stock.infra.brand.in;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.app.brand.handler.IBrandHandler;
import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.exception.ex.BrandAlreadyExistException;
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

@WebMvcTest(BrandController.class)
class BrandControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IBrandHandler brandHandler;

    @ParameterizedTest
    @ValueSource(strings = {"null", "", "missing"})
    void shouldReturnBadRequestForInvalidBrandName(String name) throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = BrandExceptionMessage.EMPTY_NAME;

        String requestBody;
        if ("missing".equals(name)) {
            requestBody = "{\"description\":\"High quality clothing\"}";
        } else if ("null".equals(name)) {
            requestBody = "{\"name\":null,\"description\":\"High quality clothing\"}";
        } else {
            requestBody = "{\"name\":\"\",\"description\":\"High quality clothing\"}";
        }

        mvc.perform(post("/brands/")
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
    void shouldReturnBadRequestForInvalidBrandDescription(String description) throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = BrandExceptionMessage.EMPTY_DESCRIPTION;

        String requestBody;
        if ("missing".equals(description)) {
            requestBody = "{\"name\":\"Nike\"}";
        } else if ("null".equals(description)) {
            requestBody = "{\"name\":\"Nike\",\"description\":null}";
        } else {
            requestBody = "{\"name\":\"Nike\",\"description\":\"" + description + "\"}";
        }

        mvc.perform(post("/brands/")
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

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":,}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfBrandDescriptionBadTypes() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_JSON;
        String expectedErrorMessage = String.format(GlobalExceptionMessage.INVALID_TYPE_PARAM, "description", "String");

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nike\", \"description\":[]}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("description"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));

    }

    @Test
    void shouldReturnBadRequestIfBrandNameBadTypes() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_JSON;
        String expectedErrorMessage = String.format(GlobalExceptionMessage.INVALID_TYPE_PARAM, "name", "String");

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":[], \"description\":\"High quality clothing\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("name"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));

    }

    @Test
    void shouldReturnCreatedWhenBrandIsSuccessfullyCreated() throws Exception {
        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nike\",\"description\":\"High quality clothing\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(brandHandler, times(1)).createBrand(any(BrandRequest.class));
    }

    @Test
    void shouldReturnConflictWhenBrandNameAlreadyExists() throws Exception {
        String expectedMessage = BrandExceptionMessage.ALREADY_EXIST_BRAND;

        doThrow(new BrandAlreadyExistException(expectedMessage)).when(brandHandler).createBrand(any(BrandRequest.class));

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nike\",\"description\":\"High quality clothing\"}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfSortDirectionIsInvalid() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = BrandExceptionMessage.INVALID_SORT_DIRECTION;

        mvc.perform(get("/brands/")
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
        String expectedErrorMessage = BrandExceptionMessage.NO_NEGATIVE_PAGE;

        mvc.perform(get("/brands/")
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
        String expectedErrorMessage = BrandExceptionMessage.GREATER_ZERO_SIZE;

        mvc.perform(get("/brands/")
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
        mvc.perform(get("/brands/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "ASC"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(brandHandler, times(1)).getBrandsByPage(0, 10, "ASC");
    }
}