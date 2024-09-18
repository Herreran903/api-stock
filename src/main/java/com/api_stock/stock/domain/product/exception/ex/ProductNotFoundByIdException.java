package com.api_stock.stock.domain.product.exception.ex;

public class ProductNotFoundByIdException extends RuntimeException {
    public ProductNotFoundByIdException(String message) {
        super(message);
    }
}
