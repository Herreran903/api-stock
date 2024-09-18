package com.api_stock.stock.infra.product.util;

public class ProductSwaggerMessages {
    private ProductSwaggerMessages(){
        throw new AssertionError();
    }

    public static final String INCREASE_STOCK_SUMMARY = "Increase product`s stock";
    public static final String INCREASE_STOCK_DESCRIPTION = "This endpoint allows increase products stock";
    public static final String INCREASE_STOCK_REQUEST_BODY_DESCRIPTION = "Request body to increase products stock";

    public static final String STOCK_REQUEST_EXAMPLE =
            "{ \"productId\": 1, " +
            "\"amount\": 2 }";

}
