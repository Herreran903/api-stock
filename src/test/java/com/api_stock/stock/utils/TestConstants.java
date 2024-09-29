package com.api_stock.stock.utils;

import java.math.BigDecimal;

public final class TestConstants {

    private TestConstants() {
        throw new AssertionError();
    }

    public static final Long VALID_CATEGORY_ID = 1L;
    public static final Long VALID_BRAND_ID = 1L;
    public static final Long VALID_PRODUCT_ID = 1L;
    public static final String VALID_CATEGORY_NAME = "Shoes";
    public static final String VALID_CATEGORY_DESCRIPTION = "Footwear";
    public static final String VALID_PRODUCT_NAME = "Jordan";
    public static final String VALID_PRODUCT_DESCRIPTION = "Latest edition";
    public static final String VALID_BRAND_NAME = "Nike";
    public static final String VALID_BRAND_DESCRIPTION = "High quality shoes";
    public static final BigDecimal VALID_PRODUCT_PRICE = BigDecimal.TEN;
    public static final Integer VALID_PRODUCT_STOCK = 5;
    public static final Integer VALID_PAGE = 0;
    public static final Integer VALID_SIZE = 1;

}
