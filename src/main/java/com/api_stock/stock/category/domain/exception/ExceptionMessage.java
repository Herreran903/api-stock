package com.api_stock.stock.category.domain.exception;

public final class ExceptionMessage {

    private ExceptionMessage(){
        throw new AssertionError();
    }

    public static final String TOO_LONG_NAME =
            "The 'name' field is too long. The maximum length allowed is 50 characters";

    public static final String TOO_LONG_DESCRIPTION =
            "The 'description' field is too long, The maximum length allowed is 90 characters";

    public static final String EMPTY_NAME =
            "The 'name' field is empty";

    public static final String EMPTY_DESCRIPTION =
            "The 'description' field is empty";

    public static final String INVALID_SORT_DIRECTION =
            "ort direction must be 'ASC' or 'DESC'";

    public static final String GREATER_ZERO_SIZE =
            "Size must be greater than zero";

    public static final String NO_NEGATIVE_PAGE =
            "Page number must be non-negative";

    public static final String ALREADY_EXIST_CATEGORY =
            "Category already exists";

    public static final String INVALID_TYPE_PARAM =
            "The parameter '%s' must be of type '%s'";

    public static final String BAD_JSON_CATEGORY =
            "The JSON object is invalid";

}
