package com.api_stock.stock.app.product.dto;

import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.util.ProductConstants;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    @NotNull(message = ProductExceptionMessage.EMPTY_NAME)
    @NotEmpty(message = ProductExceptionMessage.EMPTY_NAME)
    private String name;

    private String description;

    @NotNull(message = ProductExceptionMessage.EMPTY_PRICE)
    @Positive(message = ProductExceptionMessage.NEGATIVE_PRICE)
    private BigDecimal price;

    @NotNull(message = ProductExceptionMessage.EMPTY_STOCK)
    @Positive(message = ProductExceptionMessage.NEGATIVE_STOCK)
    private int stock;

    @NotNull(message = ProductExceptionMessage.EMPTY_BRAND_ID)
    private Long brandId;

    @NotNull(message = ProductExceptionMessage.EMPTY_CATEGORIES_IDS)
    @Size(min = ProductConstants.MIN_CATEGORIES_IDS,
          max = ProductConstants.MAX_CATEGORIES_IDS,
          message = ProductExceptionMessage.SIZE_CATEGORIES_ID)
    @UniqueElements(message = ProductExceptionMessage.UNIQUE_CATEGORIES_IDS)
    private List<Long> categoryIds;
}
