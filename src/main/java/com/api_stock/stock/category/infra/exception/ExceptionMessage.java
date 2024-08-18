package com.api_stock.stock.category.infra.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    CATEGORY_ALREADY_EXISTS("There is already a category with that name"),
    FIELD_TOO_LONG("The field '%s' exceeds the maximum length of %d characters"),
    FIELD_EMPTY("The field '%s' is empty");

    private final String message;

}
