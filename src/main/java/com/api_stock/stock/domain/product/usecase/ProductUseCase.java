package com.api_stock.stock.domain.product.usecase;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.category.model.Category;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.api.IProductServicePort;
import com.api_stock.stock.domain.product.exception.ex.ProductNotFoundByIdException;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidFieldException;
import com.api_stock.stock.domain.product.exception.ex.ProductNotValidParameterException;
import com.api_stock.stock.domain.product.exception.ex.StockNotValidFieldException;
import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalConstants;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.api_stock.stock.domain.product.exception.ProductExceptionMessage.*;
import static com.api_stock.stock.domain.product.util.ProductConstants.*;
import static com.api_stock.stock.domain.util.GlobalConstants.ASC;
import static com.api_stock.stock.domain.util.GlobalConstants.DESC;
import static com.api_stock.stock.domain.util.GlobalExceptionMessage.*;

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
                throw new ProductNotValidFieldException(UNIQUE_CATEGORIES_IDS);
            }

            if (categories.isEmpty() || categories.size() > MAX_CATEGORIES_IDS) {
                throw new ProductNotValidFieldException(SIZE_CATEGORIES_ID);
            }

        } else {
            throw new ProductNotValidFieldException(EMPTY_CATEGORIES_IDS);
        }

        if (productName == null || productName.trim().isEmpty()) {
            throw new ProductNotValidFieldException(EMPTY_NAME);
        }

        if (price == null) {
            throw new ProductNotValidFieldException(EMPTY_PRICE);
        }

        if (price.compareTo(BigDecimal.ZERO) < ProductConstants.MIN_PRICE) {
            throw new ProductNotValidFieldException(NEGATIVE_PRICE);
        }

        if (stock == null) {
            throw new ProductNotValidFieldException(EMPTY_STOCK);
        }

        if (stock < ProductConstants.MIN_STOCK) {
            throw new ProductNotValidFieldException(NEGATIVE_STOCK);
        }

        if (brand == null) {
            throw new ProductNotValidFieldException(EMPTY_BRAND_ID);
        }

        productPersistencePort.createProduct(product);
    }

    @Override
    public PageData<Product> getCategoriesByPage(int page, int size, String sortDirection, String sortProperty) {
        if (!(NAME.equalsIgnoreCase(sortProperty) || BRAND.equalsIgnoreCase(sortProperty) ||
                CATEGORIES.equalsIgnoreCase(sortProperty)))
            throw new ProductNotValidParameterException(INVALID_PROPERTY_DIRECTION);

        if (!(ASC.equalsIgnoreCase(sortDirection) || DESC.equalsIgnoreCase(sortDirection)))
            throw new ProductNotValidParameterException(INVALID_SORT_DIRECTION);

        if (page < GlobalConstants.MIN_PAGE_NUMBER)
            throw new ProductNotValidParameterException(NO_NEGATIVE_PAGE);

        if (size < GlobalConstants.MIN_PAGE_SIZE)
            throw new ProductNotValidParameterException(GREATER_ZERO_SIZE);

        return productPersistencePort.getCategoriesByPage(page, size, sortDirection, sortProperty);
    }

    @Override
    public void updateStock(Long productId, int amount) {

        if (productId == null)
            throw new StockNotValidFieldException(EMPTY_PRODUCT);

        Product product = productPersistencePort.getProductById(productId)
                        .orElseThrow(() -> new ProductNotFoundByIdException(NO_FOUND_PRODUCT));

        product.setStock(product.getStock() + amount);

        productPersistencePort.updateProduct(product);
    }
}
