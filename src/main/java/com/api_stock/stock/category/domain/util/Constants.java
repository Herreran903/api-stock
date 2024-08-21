package com.api_stock.stock.category.domain.util;

public final class Constants {

    private Constants() {
        throw new AssertionError();
    }

    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 90;

    public enum Field {
        CATEGORY,
        NAME,
        DESCRIPTION
    }

    public enum Sort {
        DESC,
        ASC,
    }
}
