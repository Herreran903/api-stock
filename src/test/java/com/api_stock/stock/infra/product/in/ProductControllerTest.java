package com.api_stock.stock.infra.product.in;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.handler.IProductHandler;
import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.util.ProductConstants;
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

import java.util.List;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters=false)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IProductHandler productHandler;

    @ParameterizedTest
    @ValueSource(strings = {"null", "", "missing"})
    void shouldReturnBadRequestForInvalidProductName(String name) throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.EMPTY_NAME;

        String requestBody = getRequestBody(name);

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
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

        mvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(productHandler, times(1)).createProduct(any(ProductRequest.class));
    }

    @Test
    void shouldReturnBadRequestIfSortDirectionIsInvalid() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = GlobalExceptionMessage.INVALID_SORT_DIRECTION;

        mvc.perform(get("/product/fetch")
                        .param("page", String.valueOf(GlobalConstants.MIN_PAGE_NUMBER))
                        .param("size", String.valueOf(GlobalConstants.MIN_PAGE_SIZE))
                        .param("sortDirection", "INVALID")
                        .param("sortProperty", ProductConstants.NAME))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("sortDirection"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestIfSortPropertyIsInvalid() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = ProductExceptionMessage.INVALID_PROPERTY_DIRECTION;

        mvc.perform(get("/product/fetch")
                        .param("page", String.valueOf(GlobalConstants.MIN_PAGE_NUMBER))
                        .param("size", String.valueOf(GlobalConstants.MIN_PAGE_SIZE))
                        .param("sortDirection", GlobalConstants.ASC)
                        .param("sortProperty", "INVALID"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("sortProperty"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestIfPageIsNegative() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_PARAMETERS;
        String expectedErrorMessage = GlobalExceptionMessage.NO_NEGATIVE_PAGE;

        mvc.perform(get("/product/fetch")
                        .param("page", "-1")
                        .param("size", String.valueOf(GlobalConstants.MIN_PAGE_SIZE))
                        .param("sortDirection", GlobalConstants.ASC)
                        .param("sortProperty", ProductConstants.NAME))
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

        mvc.perform(get("/product/fetch")
                        .param("page", String.valueOf(GlobalConstants.MIN_PAGE_NUMBER))
                        .param("size", "0")
                        .param("sortDirection", GlobalConstants.ASC)
                        .param("sortProperty", ProductConstants.NAME))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("size"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnOkWhenParametersAreValid() throws Exception {
        mvc.perform(get("/product/fetch")
                        .param("page", String.valueOf(GlobalConstants.MIN_PAGE_NUMBER))
                        .param("size", String.valueOf(GlobalConstants.MIN_PAGE_SIZE))
                        .param("sortDirection", GlobalConstants.ASC)
                        .param("sortProperty", ProductConstants.NAME))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productHandler, times(1)).getProductsByPage(
                GlobalConstants.MIN_PAGE_NUMBER,
                GlobalConstants.MIN_PAGE_SIZE,
                GlobalConstants.ASC,
                ProductConstants.NAME);
    }

    @Test
    void shouldReturnBadRequestForInvalidProductId() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.EMPTY_PRODUCT;

        String requestBody =
                "{\"product\":\"\", \"amount\":\"2\"}";

        mvc.perform(post("/product/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("product"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnBadRequestForInvalidAmount() throws Exception {
        String expectedMessage = GlobalExceptionMessage.INVALID_OBJECT;
        String expectedErrorMessage = ProductExceptionMessage.EMPTY_AMOUNT;

        String requestBody =
                "{\"product\":\"1\", \"amount\":\"\"}";

        mvc.perform(post("/product/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage))
                .andExpect(jsonPath("$.errors[0].field").value("amount"))
                .andExpect(jsonPath("$.errors[0].message").value(expectedErrorMessage));
    }

    @Test
    void shouldReturnOkForValidStockRequest() throws Exception {
        String requestBody =
                "{\"product\":\"1\", \"amount\":\"2\"}";

        mvc.perform(post("/product/increase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}