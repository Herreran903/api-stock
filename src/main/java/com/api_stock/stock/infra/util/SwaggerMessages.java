package com.api_stock.stock.infra.util;

public class SwaggerMessages {
    private SwaggerMessages() {
        throw new AssertionError();
    }

    public static final String RESPONSE_204_DESCRIPTION = "No content";
    public static final String RESPONSE_400_DESCRIPTION = "Invalid input";
    public static final String RESPONSE_401_DESCRIPTION = "Unauthorized";
    public static final String RESPONSE_403_DESCRIPTION = "Forbidden";
    public static final String RESPONSE_500_DESCRIPTION = "Internal server error";

    public static final String CODE_200 = "200";
    public static final String CODE_201 = "201";
    public static final String CODE_204 = "204";
    public static final String CODE_400 = "400";
    public static final String CODE_401 = "401";
    public static final String CODE_403 = "403";
    public static final String CODE_500 = "500";
}
