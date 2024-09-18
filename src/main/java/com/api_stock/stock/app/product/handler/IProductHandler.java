package com.api_stock.stock.app.product.handler;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.dto.ProductResponse;
import com.api_stock.stock.app.product.dto.StockRequest;
import com.api_stock.stock.domain.page.PageData;

public interface IProductHandler {
    void createProduct(ProductRequest productRequest);
    PageData<ProductResponse> getProductsByPage(int page, int size, String sortDirection, String sortProperty);
    void updateStock(StockRequest stockRequest);
}
