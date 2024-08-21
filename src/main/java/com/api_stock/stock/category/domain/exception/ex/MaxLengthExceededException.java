package com.api_stock.stock.category.domain.exception.ex;

public class MaxLengthExceededException extends RuntimeException {

    public MaxLengthExceededException (String message) {
        super(message);
    }
}
