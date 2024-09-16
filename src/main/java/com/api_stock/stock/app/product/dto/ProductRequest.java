package com.api_stock.stock.app.product.dto;

import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.util.List;

import static com.api_stock.stock.domain.product.exception.ProductExceptionMessage.*;
import static com.api_stock.stock.domain.product.util.ProductConstants.MAX_CATEGORIES_IDS;
import static com.api_stock.stock.domain.product.util.ProductConstants.MIN_CATEGORIES_IDS;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = EMPTY_NAME)
    @NotEmpty(message = EMPTY_NAME)
    private String name;

    private String description;

    @NotNull(message = EMPTY_PRICE)
    @Positive(message = NEGATIVE_PRICE)
    private BigDecimal price;

    @NotNull(message = EMPTY_STOCK)
    @Positive(message = NEGATIVE_STOCK)
    private int stock;

    @NotNull(message = EMPTY_BRAND_ID)
    private Long brandId;

    @NotNull(message = EMPTY_CATEGORIES_IDS)
    @Size(min = MIN_CATEGORIES_IDS, max = MAX_CATEGORIES_IDS, message = SIZE_CATEGORIES_ID)
    @UniqueElements(message = ProductExceptionMessage.UNIQUE_CATEGORIES_IDS)
    private List<Long> categoryIds;
}
