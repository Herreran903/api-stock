package com.api_stock.stock.category.domain.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DomainExceptionMessage {

    FIELD_TOO_LONG("The field '%s' exceeds the maximum length of %d characters"),
    FIELD_EMPTY("The field '%s' is empty");

    private final String message;

    public String getMessage(String field, int maxLength) {
        return String.format(message, field, maxLength);
    }

}
