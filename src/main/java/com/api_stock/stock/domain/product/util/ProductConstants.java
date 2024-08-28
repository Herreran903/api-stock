package com.api_stock.stock.domain.product.util;

public final class ProductConstants {

    private ProductConstants() {
        throw new AssertionError();
    }

    public static final int MIN_CATEGORIES_IDS = 1;
    public static final int MAX_CATEGORIES_IDS = 3;
    public static final int MIN_PRICE = 0;
    public static final int MIN_STOCK = 0;

    public enum Field {
        PRODUCT,
        NAME,
        DESCRIPTION,
        PRICE,
        STOCK,
        BRAND,
        CATEGORIES,
    }
}
