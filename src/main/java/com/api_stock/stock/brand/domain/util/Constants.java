package com.api_stock.stock.brand.domain.util;

public class Constants {

    private Constants() {
        throw new AssertionError();
    }

    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_DESCRIPTION_LENGTH = 120;

    public enum Field {
        BRAND,
        NAME,
        DESCRIPTION
    }

    public enum Sort {
        DESC,
        ASC,
    }
}
