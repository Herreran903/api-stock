package com.api_stock.stock.brand.domain.exception.ex;

public class BrandAlreadyExistException extends RuntimeException {
    public BrandAlreadyExistException(String message) {
        super(message);
    }
}
