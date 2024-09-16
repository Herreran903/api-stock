package com.api_stock.stock.domain.util;

public final class GlobalConstants {
    private GlobalConstants() {
        throw new AssertionError();
    }

    public static final int MIN_PAGE_NUMBER = 0;
    public static final int MIN_PAGE_SIZE = 1;

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";

    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    public static final String ORDER_REGEX = ASC + "|" + DESC;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final int TOKEN_SUBSTRING = 7;

    public static final String ROLES = "roles";
}
