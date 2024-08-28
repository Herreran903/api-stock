package com.api_stock.stock.domain.category.exception.ex;

import java.util.List;

public class CategoriesNotFoundByIdsException extends RuntimeException {
    private final List<Long> missingIds;

    public CategoriesNotFoundByIdsException(String message, List<Long> missingIds) {
        super(message);
        this.missingIds = missingIds;
    }

    public List<Long> getMissingIds() {
        return missingIds;
    }
}
