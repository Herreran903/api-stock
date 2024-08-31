package com.api_stock.stock.domain.product.util;

public final class ProductConstants {

    private ProductConstants() {
        throw new AssertionError();
    }

    public static final int MIN_CATEGORIES_IDS = 1;
    public static final int MAX_CATEGORIES_IDS = 3;
    public static final int MIN_PRICE = 0;
    public static final int MIN_STOCK = 0;
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String CATEGORIES = "categories";
    public static final String PROPERTY_REGEX = NAME + "|" + BRAND + "|" + CATEGORIES;
    public static final String BRAND_NAME = BRAND + "." + NAME;
}
