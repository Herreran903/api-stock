package com.api_stock.stock.domain.product.usecase;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.api.IProductServicePort;
import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidFieldException;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidParameterException;
import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductUseCase implements IProductServicePort {

    private final IProductPersistencePort productPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public void createProduct(Product product) {

        String productName = product.getName();
        BigDecimal price = product.getPrice();
        Integer stock = product.getStock();
        Brand brand = product.getBrand();
        List<Category> categories = product.getCategories();

        if (categories != null) {
            Set<Long> uniqueCategoryIds = categories.stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());

            if (uniqueCategoryIds.size() != categories.size()) {
                throw new ProductNotValidFieldException(ProductExceptionMessage.UNIQUE_CATEGORIES_IDS);
            }

            if (categories.isEmpty() || categories.size() > ProductConstants.MAX_CATEGORIES_IDS) {
                throw new ProductNotValidFieldException(ProductExceptionMessage.SIZE_CATEGORIES_ID);
            }

        } else {
            throw new ProductNotValidFieldException(ProductExceptionMessage.EMPTY_CATEGORIES_IDS);
        }

        if (productName == null || productName.trim().isEmpty()) {
            throw new ProductNotValidFieldException(ProductExceptionMessage.EMPTY_NAME);
        }

        if (price == null) {
            throw new ProductNotValidFieldException(ProductExceptionMessage.EMPTY_PRICE);
        }

        if (price.compareTo(BigDecimal.ZERO) < ProductConstants.MIN_PRICE) {
            throw new ProductNotValidFieldException(ProductExceptionMessage.NEGATIVE_PRICE);
        }

        if (stock == null) {
            throw new ProductNotValidFieldException(ProductExceptionMessage.EMPTY_STOCK);
        }

        if (stock < ProductConstants.MIN_STOCK) {
            throw new ProductNotValidFieldException(ProductExceptionMessage.NEGATIVE_STOCK);
        }

        if (brand == null) {
            throw new ProductNotValidFieldException(ProductExceptionMessage.EMPTY_BRAND_ID);
        }

        productPersistencePort.createProduct(product);
    }

    @Override
    public PageData<Product> getCategoriesByPage(int page, int size, String sortDirection, String sortProperty) {

        if (!(ProductConstants.NAME.equalsIgnoreCase(sortProperty) || ProductConstants.BRAND.equalsIgnoreCase(sortProperty) ||
                ProductConstants.CATEGORIES.equalsIgnoreCase(sortProperty)))
            throw new ProductNotValidParameterException(ProductExceptionMessage.INVALID_PROPERTY_DIRECTION);

        if (!(GlobalConstants.ASC.equalsIgnoreCase(sortDirection) || GlobalConstants.DESC.equalsIgnoreCase(sortDirection)))
            throw new ProductNotValidParameterException(GlobalExceptionMessage.INVALID_SORT_DIRECTION);

        if (page < GlobalConstants.MIN_PAGE_NUMBER)
            throw new ProductNotValidParameterException(GlobalExceptionMessage.NO_NEGATIVE_PAGE);

        if (size < GlobalConstants.MIN_PAGE_SIZE)
            throw new ProductNotValidParameterException(GlobalExceptionMessage.GREATER_ZERO_SIZE);

        return productPersistencePort.getCategoriesByPage(page, size, sortDirection, sortProperty);
    }
}
