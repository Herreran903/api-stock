package com.api_stock.stock.domain.product.exception;

import com.api_stock.stock.domain.product.util.ProductConstants;

public final class ProductExceptionMessage {
    private ProductExceptionMessage() {
        throw new AssertionError();
    }

    public static final String EMPTY_NAME =
            "The 'name' field is empty";

    public static final String EMPTY_PRICE =
            "The 'price' field is empty";

    public static final String NEGATIVE_PRICE =
            "The 'price' field must be a positive number";

    public static final String EMPTY_STOCK =
            "The 'stock' field is empty";

    public static final String NEGATIVE_STOCK =
            "The 'stock' field must be a non-negative integer";

    public static final String EMPTY_BRAND_ID =
            "The 'brandId' field is empty";

    public static final String EMPTY_CATEGORIES_IDS =
            "The 'categoryIds' field is empty";

    public static final String SIZE_CATEGORIES_ID =
            "The 'categoryIds' field must contain between " +
                    ProductConstants.MIN_CATEGORIES_IDS + " and " +
                    ProductConstants.MAX_CATEGORIES_IDS + " category IDs.";

    public static final String UNIQUE_CATEGORIES_IDS =
            "The 'categoryIds' field contains duplicate category IDs";

    public static final String INVALID_PROPERTY_DIRECTION =
            "Sort direction must be 'name' or 'brand' or 'category'";

    public static final String EMPTY_PRODUCT =
            "The 'product' field is empty";

    public static final String EMPTY_AMOUNT =
            "The 'amount' field is empty";

    public static final String NO_FOUND_PRODUCT =
            "Product does not found";

}

