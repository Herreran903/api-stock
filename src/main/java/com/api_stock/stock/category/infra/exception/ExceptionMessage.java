package com.api_stock.stock.category.infra.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    CATEGORY_ALREADY_EXISTS("There is already a category with that name");

    private final String message;

}
