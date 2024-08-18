package com.api_stock.stock.category.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails {

    private int statusCode;
    private String message;
    private String details;
}
