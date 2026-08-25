package com.api_stock.stock.app.product.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryIdListResponse {
    private List<String> categories;
}
