package com.api_stock.stock.brand.infra.in;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.app.handler.IBrandHandler;
import com.api_stock.stock.brand.domain.exception.BrandExceptionMessage;
import com.api_stock.stock.brand.domain.exception.ex.BrandAlreadyExistException;
import com.api_stock.stock.brand.domain.exception.ex.BrandNotValidFieldException;
import com.api_stock.stock.brand.domain.exception.ex.BrandNotValidParameterException;
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

@WebMvcTest(BrandController.class)
class BrandControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IBrandHandler brandHandler;

    @Test
    void shouldReturnBadRequestIfBrandNameIsNull() throws Exception {
        String expectedMessage = BrandExceptionMessage.EMPTY_NAME;

        doAnswer(invocation -> {
            BrandRequest request = invocation.getArgument(0);
            if (request.getName() == null) {
                throw new BrandNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(brandHandler).createBrand(any(BrandRequest.class));

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":null,\"description\":\"High quality clothing\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfBrandNameIsEmpty() throws Exception {
        String expectedMessage = BrandExceptionMessage.EMPTY_NAME;

        doAnswer(invocation -> {
            BrandRequest request = invocation.getArgument(0);
            if (request.getName().isEmpty()) {
                throw new BrandNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(brandHandler).createBrand(any(BrandRequest.class));

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"High quality clothing\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfBrandNameIsMissing() throws Exception {
        String expectedMessage = BrandExceptionMessage.EMPTY_NAME;

        doAnswer(invocation -> {
            BrandRequest request = invocation.getArgument(0);
            if (request.getName() == null || request.getName().isEmpty()) {
                throw new BrandNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(brandHandler).createBrand(any(BrandRequest.class));

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"High quality clothing\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));

    }

    @Test
    void shouldReturnBadRequestIfBrandDescriptionIsNull() throws Exception {
        String expectedMessage = BrandExceptionMessage.EMPTY_DESCRIPTION;

        doAnswer(invocation -> {
            BrandRequest request = invocation.getArgument(0);
            if (request.getDescription() == null) {
                throw new BrandNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(brandHandler).createBrand(any(BrandRequest.class));

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nike\",\"description\":null}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfBrandDescriptionIsEmpty() throws Exception {
        String expectedMessage = BrandExceptionMessage.EMPTY_DESCRIPTION;

        doAnswer(invocation -> {
            BrandRequest request = invocation.getArgument(0);
            if (request.getDescription().isEmpty()) {
                throw new BrandNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(brandHandler).createBrand(any(BrandRequest.class));

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nike\",\"description\":\"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfBrandDescriptionIsMissing() throws Exception {
        String expectedMessage = BrandExceptionMessage.EMPTY_DESCRIPTION;

        doAnswer(invocation -> {
            BrandRequest request = invocation.getArgument(0);
            if (request.getDescription() == null || request.getDescription().isEmpty()) {
                throw new BrandNotValidFieldException(expectedMessage);
            }
            return null;
        }).when(brandHandler).createBrand(any(BrandRequest.class));

        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nike\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnCreatedWhenBrandIsSuccessfullyCreated() throws Exception {
        mvc.perform(post("/brands/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Nike\",\"description\":\"High quality clothing\"}"))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(brandHandler).createBrand(any(BrandRequest.class));
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
        String expectedMessage = BrandExceptionMessage.INVALID_SORT_DIRECTION;

        when(brandHandler.getBrandsByPage(0,10,"INVALID")).
                thenThrow(new BrandNotValidParameterException(expectedMessage));

        mvc.perform(get("/brands/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortDirection", "INVALID"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfPageIsNegative() throws Exception {
        String expectedMessage = BrandExceptionMessage.NO_NEGATIVE_PAGE;

        when(brandHandler.getBrandsByPage(-1,10,"ASC")).
                thenThrow(new BrandNotValidParameterException(expectedMessage));

        mvc.perform(get("/brands/")
                        .param("page", "-1")
                        .param("size", "10")
                        .param("sortDirection", "ASC"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));

    }

    @Test
    void shouldReturnBadRequestIfSizeIsZeroOrNegative() throws Exception {
        String expectedMessage = BrandExceptionMessage.GREATER_ZERO_SIZE;

        when(brandHandler.getBrandsByPage(0,0,"ASC")).
                thenThrow(new BrandNotValidParameterException(expectedMessage));

        mvc.perform(get("/brands/")
                        .param("page", "0")
                        .param("size", "0")
                        .param("sortDirection", "ASC"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
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