package com.api_stock.stock.app.product.dto;

import com.api_stock.stock.domain.page.PageData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageResponse {
    private PageData<CartProductResponse> page;
    private BigDecimal total;
}
