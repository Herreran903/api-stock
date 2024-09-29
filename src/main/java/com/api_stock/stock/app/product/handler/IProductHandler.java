package com.api_stock.stock.app.product.handler;

import com.api_stock.stock.app.product.dto.*;
import com.api_stock.stock.domain.page.PageData;

import java.math.BigDecimal;
import java.util.Map;

public interface IProductHandler {
    void createProduct(ProductRequest productRequest);
    PageData<ProductResponse> getProductsByPage(int page, int size, String order, String sortProperty);
    void updateStock(StockRequest stockRequest);
    CategoryIdListResponse getListCategoriesOfProducts(ProductIdListRequest productIdListRequest);
    Integer getStockOfProduct(ProductIdRequest productIdRequest);
    PageData<CartProductResponse> getProductsByPageAndIds(Integer page, Integer size, String order, String category, String brand, ProductIdListRequest productIdListRequest);
    Map<Long, BigDecimal> getProductsPrice(ProductIdListRequest productIdListRequest);
}
