package com.api_stock.stock.category.infra.in;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.handler.ICategoryHandler;
import com.api_stock.stock.category.domain.exception.ex.EmptyFieldException;
import com.api_stock.stock.category.infra.exception.ex.CategoryAlreadyExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ICategoryHandler categoryHandler;

    @Test
    void shouldReturnBadRequestIfCategoryNameIsNull() throws Exception {
        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName() == null) {
                throw new EmptyFieldException("name");
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":null,\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestIfCategoryNameIsEmpty() throws Exception {
        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName().isEmpty()) {
                throw new EmptyFieldException("name");
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestIfCategoryNameIsMissing() throws Exception {
        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getName() == null || request.getName().isEmpty()) {
                throw new EmptyFieldException("name");
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsNull() throws Exception {
        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription() == null) {
                throw new EmptyFieldException("description");
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":null}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsEmpty() throws Exception {
        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription().isEmpty()) {
                throw new EmptyFieldException("description");
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestIfCategoryDescriptionIsMissing() throws Exception {
        doAnswer(invocation -> {
            CategoryRequest request = invocation.getArgument(0);
            if (request.getDescription() == null || request.getDescription().isEmpty()) {
                throw new EmptyFieldException("description");
            }
            return null;
        }).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCreatedWhenCategoryIsSuccessfullyCreated() throws Exception {
        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnConflictWhenCategoryNameAlreadyExists() throws Exception {
        doThrow(new CategoryAlreadyExistException()).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mvc.perform(post("/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}"))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}