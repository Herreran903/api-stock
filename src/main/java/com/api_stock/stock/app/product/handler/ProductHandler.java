package com.api_stock.stock.app.product.handler;

import com.api_stock.stock.app.product.dto.*;
import com.api_stock.stock.app.product.mapper.IProductRequestMapper;
import com.api_stock.stock.app.product.mapper.IProductResponseMapper;
import com.api_stock.stock.domain.brand.api.IBrandServicePort;
import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.api.ICategoryServicePort;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.api.IProductServicePort;
import com.api_stock.stock.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductHandler implements IProductHandler {
    private final IProductServicePort productServicePort;
    private final IProductRequestMapper productRequestMapper;
    private final IProductResponseMapper productResponseMapper;
    private final IBrandServicePort brandServicePort;
    private final ICategoryServicePort categoryServicePort;

    @Override
    public void createProduct(ProductRequest productRequest) {
        Brand brand = brandServicePort.getBrandById(productRequest.getBrandId());
        List<Category> categories = categoryServicePort.getCategoriesByIds(productRequest.getCategoryIds());

        Product product = productRequestMapper.toProduct(productRequest);

        product.setBrand(brand);
        product.setCategories(categories);

        productServicePort.createProduct(product);
    }

    @Override
    public PageData<ProductResponse> getProductsByPage(int page, int size, String order, String sortProperty) {
        PageData<Product> pageData = productServicePort.getCategoriesByPage(page, size, order, sortProperty);

        return productResponseMapper.toPageResponse(pageData);
    }

    @Override
    public void updateStock(StockRequest stockRequest) {
        productServicePort.updateStock(stockRequest.getProduct(), stockRequest.getAmount());
    }

    @Override
    public CategoryIdListResponse getListCategoriesOfProducts(ProductIdListRequest productIdListRequest) {
        CategoryIdListResponse categoryIdListResponse = new CategoryIdListResponse();
        categoryIdListResponse.setCategories(productServicePort.getListCategoriesOfProducts(productIdListRequest.getProducts()));

        return categoryIdListResponse;
    }

    @Override
    public Integer getStockOfProduct(ProductIdRequest productIdRequest) {
        return productServicePort.getStockOfProduct(productIdRequest.getProduct());
    }

    @Override
    public PageData<CartProductResponse> getProductsByPageAndIds(Integer page, Integer size, String order, String category, String brand, ProductIdListRequest productIdListRequest) {
        PageData<Product> pageData = productServicePort.getProductsByPageAndIds(page, size, order, category, brand, productIdListRequest.getProducts());
        return productResponseMapper.toPageCartResponse(pageData);
    }

    @Override
    public Map<Long, BigDecimal> getProductsPrice(ProductIdListRequest productIdListRequest) {
        return productServicePort.getProductsPrice(productIdListRequest.getProducts());
    }
}
