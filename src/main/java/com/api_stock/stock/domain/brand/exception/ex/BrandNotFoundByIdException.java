package com.api_stock.stock.domain.brand.exception.ex;

public class BrandNotFoundByIdException extends RuntimeException {
    public BrandNotFoundByIdException(String message) {
        super(message);
    }
}
