package com.api_stock.stock.domain.category.exception;

import com.api_stock.stock.domain.category.util.CategoryConstants;

public final class CategoryExceptionMessage {

    private CategoryExceptionMessage() {
        throw new AssertionError();
    }

    public static final String TOO_LONG_NAME =
            "The 'name' field is too long. " +
                    "The maximum length allowed is '" + CategoryConstants.MAX_NAME_LENGTH + "' characters";

    public static final String TOO_LONG_DESCRIPTION =
            "The 'description' field is too long. " +
                    "The maximum length allowed is '" + CategoryConstants.MAX_DESCRIPTION_LENGTH + "' characters";

    public static final String EMPTY_NAME =
            "The 'name' field is empty";

    public static final String EMPTY_DESCRIPTION =
            "The 'description' field is empty";

    public static final String ALREADY_EXIST_CATEGORY =
            "Category already exists";

    public static final String NO_FOUND_CATEGORIES =
            "Some categories were not found";
}