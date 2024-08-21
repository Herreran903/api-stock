package com.api_stock.stock.category.infra.in;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.exception.AppExceptionMessage;
import com.api_stock.stock.category.app.exception.ex.InvalidParameterException;
import com.api_stock.stock.category.app.handler.ICategoryHandler;
import com.api_stock.stock.category.domain.exception.DomainExceptionMessage;
import com.api_stock.stock.category.domain.exception.ex.EmptyFieldException;
import com.api_stock.stock.category.infra.exception.InfraExceptionMessage;
import com.api_stock.stock.category.infra.exception.ex.CategoryAlreadyExistException;
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
        String expectedMessage = DomainExceptionMessage.FIELD_EMPTY.getMessage("name", 0);

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName() == null) {
                throw new EmptyFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":null,\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryNameIsEmpty() throws Exception {
        String expectedMessage = DomainExceptionMessage.FIELD_EMPTY.getMessage("name", 0);

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName().isEmpty()) {
                throw new EmptyFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryNameIsMissing() throws Exception {
        String expectedMessage = DomainExceptionMessage.FIELD_EMPTY.getMessage("name", 0);

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName() == null || request.getName().isEmpty()) {
                throw new EmptyFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));

    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsNull() throws Exception {
        String expectedMessage = DomainExceptionMessage.FIELD_EMPTY.getMessage("description", 0);

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription() == null) {
                throw new EmptyFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":null}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsEmpty() throws Exception {
        String expectedMessage = DomainExceptionMessage.FIELD_EMPTY.getMessage("description", 0);

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription().isEmpty()) {
                throw new EmptyFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsMissing() throws Exception {
        String expectedMessage = DomainExceptionMessage.FIELD_EMPTY.getMessage("description", 0);

        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription() == null || request.getDescription().isEmpty()) {
                throw new EmptyFieldException(expectedMessage);
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

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

        verify(categoryHandler).createCategory(any(CategoryRequest.class));
    }

    @Test
    void shouldReturnConflictWhenCategoryNameAlreadyExists() throws Exception {
        String expectedMessage = InfraExceptionMessage.CATEGORY_ALREADY_EXISTS.getMessage();

        doThrow(new CategoryAlreadyExistException(expectedMessage)).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestIfSortDirectionIsInvalid() throws Exception {
        String expectedMessage = AppExceptionMessage.INVALID_SORT_DIRECTION.getMessage();

        when(categoryHandler.getCategoriesByPage(0,10,"INVALID")).
                thenThrow(new InvalidParameterException(expectedMessage));

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
        String expectedMessage = AppExceptionMessage.NO_NEGATIVE_PAGE.getMessage();

        when(categoryHandler.getCategoriesByPage(-1,10,"ASC")).
                thenThrow(new InvalidParameterException(expectedMessage));

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
        String expectedMessage = AppExceptionMessage.SIZE_GREATER_ZERO.getMessage();

        when(categoryHandler.getCategoriesByPage(0,0,"ASC")).
                thenThrow(new InvalidParameterException(expectedMessage));

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

        verify(categoryHandler).getCategoriesByPage(0, 10, "ASC");
    }
}