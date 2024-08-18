package com.api_stock.stock.category.domain.exception.ex;

import com.api_stock.stock.category.domain.exception.ExceptionMessage;

public class MaxLengthExceededException extends RuntimeException {

    public MaxLengthExceededException (String field, int maxLength) {
        super(ExceptionMessage.FIELD_TOO_LONG.getMessage(field, maxLength));
    }
}
