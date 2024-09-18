package com.api_stock.stock.app.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.api_stock.stock.domain.product.exception.ProductExceptionMessage.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
    @NotNull(message = EMPTY_PRODUCT)
    private Long product;

    @NotNull(message = EMPTY_AMOUNT)
    private int amount;
}
