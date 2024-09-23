package com.api_stock.stock.domain.product.util;

public final class ProductConstants {
    private ProductConstants() {
        throw new AssertionError();
    }

    public static final int MIN_CATEGORIES_IDS = 1;
    public static final int MAX_CATEGORIES_IDS = 3;
    public static final int MIN_PRICE = 0;
    public static final int MIN_STOCK = 0;

    public static final String PRODUCT_ID = "id";
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String CATEGORIES = "categories";
    public static final String PROPERTY_REGEX = NAME + "|" + BRAND + "|" + CATEGORIES;
    public static final String BRAND_NAME = BRAND + "." + NAME;

    public static final String BRAND_ID_COLUMN = "brand_id";
    public static final String PRODUCT_ID_COLUMN = "product_id";
    public static final String CATEGORY_ID_COLUMN = "category_id";

    public static final String PRODUCT_TABLE_NAME = "product";
    public static final String PRODUCT_CATEGORIES_TABLE_NAME = "product_categories";
}
