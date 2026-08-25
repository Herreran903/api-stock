package com.api_stock.stock.app.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductIdListRequest {
    @NotEmpty
    @NotNull
    private List<Long> products;
}
