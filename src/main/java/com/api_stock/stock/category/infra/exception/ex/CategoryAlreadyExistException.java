package com.api_stock.stock.category.infra.exception.ex;

import com.api_stock.stock.category.infra.exception.ExceptionMessage;

public class CategoryAlreadyExistException extends RuntimeException {

    public CategoryAlreadyExistException() {
        super(ExceptionMessage.CATEGORY_ALREADY_EXISTS.getMessage());
    }
}
