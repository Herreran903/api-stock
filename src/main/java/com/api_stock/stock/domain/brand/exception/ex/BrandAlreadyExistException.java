package com.api_stock.stock.domain.brand.exception.ex;

public class BrandAlreadyExistException extends RuntimeException {
    public BrandAlreadyExistException(String message) {
        super(message);
    }
}
