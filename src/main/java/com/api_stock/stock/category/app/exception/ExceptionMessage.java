package com.api_stock.stock.category.app.exception;

public enum ExceptionMessage {
    INVALID_SORT_DIRECTION("Sort direction must be 'ASC' or 'DESC'"),
    SIZE_GREATER_ZERO("Size must be greater than zero"),
    NO_NEGATIVE_PAGE("Page number must be non-negative");

    private final String message;

    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
