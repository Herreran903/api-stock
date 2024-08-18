package com.api_stock.stock.category.domain.exception.ex;

import com.api_stock.stock.category.domain.exception.ExceptionMessage;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(String field) {
        super(ExceptionMessage.FIELD_EMPTY.getMessage(field, 0));
    }
}
