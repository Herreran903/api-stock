package com.api_stock.stock.category.domain.model;

import com.api_stock.stock.common.page.Page;

import java.util.List;

public class CategoryPage<T> extends Page {
    private List<T> data;

    public CategoryPage(List<T> data,
                        int pageNumber,
                        int totalElements,
                        boolean isFirst,
                        boolean isLast,
                        boolean hasNext,
                        boolean hasPrevious) {
        super(pageNumber, totalElements, isFirst, isLast, hasNext, hasPrevious);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
