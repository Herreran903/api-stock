package com.api_stock.stock.category.domain.exception.ex;

public class EmptyFieldException extends RuntimeException {

    public EmptyFieldException(String message) {
        super(message);
    }
}
