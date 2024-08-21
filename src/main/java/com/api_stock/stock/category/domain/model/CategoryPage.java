package com.api_stock.stock.category.domain.model;

import java.util.List;

public class CategoryPage<T> {
    private List<T> data;
    int pageNumber;
    int totalElements;
    boolean isFirst;
    boolean isLast;
    boolean hasNext;
    boolean hasPrevious;

    public CategoryPage(List<T> data,
                        int pageNumber,
                        int totalElements,
                        boolean isFirst,
                        boolean isLast,
                        boolean hasNext,
                        boolean hasPrevious) {
        this.data = data;
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }
}
