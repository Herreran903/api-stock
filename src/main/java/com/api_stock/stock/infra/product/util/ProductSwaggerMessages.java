package com.api_stock.stock.infra.product.util;

public class ProductSwaggerMessages {
    private ProductSwaggerMessages(){
        throw new AssertionError();
    }

    public static final String INCREASE_STOCK_REQUEST_EXAMPLE =
            "{\"product\": 1," +
            "\"amount\": 2 }";

    public static final String CREATE_PRODUCT_REQUEST_EXAMPLE =
            "{\"name\":\"Smartphone\"," +
            "\"description\":\"Latest model smartphone\"," +
            "\"price\":699.99," +
            "\"stock\":50," +
            "\"brandId\":1," +
            "\"categoryIds\":[1,2,3] }";

    public static final String GET_STOCK_REQUEST_EXAMPLE =
            "{\"product\": 1 }";

    public static final String GET_CATEGORIES_REQUEST_EXAMPLE =
            "{\"products\": [1,2,3] }";

    public static final String GET_PRICES_REQUEST_EXAMPLE =
            "{\"products\": [1,2,3] }";

    public static final String FETCH_PRODUCT_CART_REQUEST_EXAMPLE =
            "{\"products\": [1,2,3] }";
}
