package com.api_stock.stock.domain.product.exception.ex;

public class StockNotValidFieldException extends RuntimeException {
    public StockNotValidFieldException(String message) {
        super(message);
    }
}
