package com.api_stock.stock.infra.product.in;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.handler.IProductHandler;
import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IProductHandler productHandler;

    @ParameterizedTest
    @ValueSource(strings = {"null", "", "missing"})
    void shouldReturnBadRequestForInvalidProductName(String name) throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.EMPTY_NAME;

        String requestBody = getRequestBody(name);

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("name"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    private static String getRequestBody(String name) {
        String requestGeneral =
                "\"description\":\"Description\"," +
                "\"price\":\"10.0\"," +
                "\"stock\":\"4\"," +
                "\"brandId\":\"1\"," +
                "\"categoryIds\":[1,2,3]}";

        String requestBody;
        if ("missing".equals(name)) {
            requestBody = "{" + requestGeneral;
        } else if ("null".equals(name)) {
            requestBody = "{\"name\":null," + requestGeneral;
        } else { // "" (empty string case)
            requestBody = "{\"name\":\"\"," + requestGeneral;
        }
        return requestBody;
    }

    @Test
    void shouldReturnBadRequestIfJsonNoValid() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_JSON;

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":,}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void shouldReturnBadRequestForNullProductPrice() throws Exception{
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.EMPTY_PRICE;

        String requestBody =
                "{\"name\":\"Name\"," +
                "\"description\":\"Description\"," +
                "\"price\":null," +
                "\"stock\":\"4\"," +
                "\"brandId\":\"1\"," +
                "\"categoryIds\":[1,2,3]}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("price"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestForNegativeProductPrice() throws Exception{
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.NEGATIVE_PRICE;

        String requestBody =
                "{\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"price\":\"-1\"," +
                        "\"stock\":\"4\"," +
                        "\"brandId\":\"1\"," +
                        "\"categoryIds\":[1,2,3]}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("price"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestForNullProductStock() throws Exception{
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.NEGATIVE_STOCK;

        String requestBody =
                "{\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"price\":\"10.0\"," +
                        "\"brandId\":\"1\"," +
                        "\"categoryIds\":[1,2,3]}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("stock"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestForNegativeProductStock() throws Exception{
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.NEGATIVE_STOCK;

        String requestBody =
                "{\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"price\":\"10.0\"," +
                        "\"stock\":\"-1\"," +
                        "\"brandId\":\"1\"," +
                        "\"categoryIds\":[1,2,3]}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("stock"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestForNullProductBrandId() throws Exception{
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.EMPTY_BRAND_ID;

        String requestBody =
                "{\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"price\":\"10.0\"," +
                        "\"stock\":\"5\"," +
                        "\"categoryIds\":[1,2,3]}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("brandId"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestForNullProductCategoryIds() throws Exception{
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.EMPTY_CATEGORIES_IDS;

        String requestBody =
                "{\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"price\":\"10.0\"," +
                        "\"stock\":\"5\"," +
                        "\"brandId\":\"1\"}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("categoryIds"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @ParameterizedTest
    @ValueSource(strings = {"min", "max"})
    void shouldReturnBadRequestForSizeProductCategoryIds(String limit) throws Exception{
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.SIZE_CATEGORIES_ID;

        List<Long> categoryIds = limit.equals("min")
                ? LongStream.range(1, ProductConstants.MIN_CATEGORIES_IDS).boxed().toList()
                : LongStream.rangeClosed(1, ProductConstants.MAX_CATEGORIES_IDS + 1).boxed().toList();

        String requestBody =
                "{\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"price\":\"10.0\"," +
                        "\"stock\":\"5\"," +
                        "\"brandId\":\"1\"," +
                        "\"categoryIds\":" + categoryIds + "}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("categoryIds"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestForNoUniqueProductCategoryIds() throws Exception{
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.UNIQUE_CATEGORIES_IDS;

        String requestBody =
                "{\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"price\":\"10.0\"," +
                        "\"stock\":\"5\"," +
                        "\"brandId\":\"1\"," +
                        "\"categoryIds\":[1,1,1]}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("categoryIds"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnCreatedWhenProductIsSuccessfullyCreated() throws Exception {

        String requestBody =
                "{\"name\":\"Name\"," +
                        "\"description\":\"Description\"," +
                        "\"price\":\"10.0\"," +
                        "\"stock\":\"5\"," +
                        "\"brandId\":\"1\"," +
                        "\"categoryIds\":[1,3,2]}";

        mvc.perform(post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(productHandler, times(1)).createProduct(any(ProductRequest.class));
    }
}