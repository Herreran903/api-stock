package com.api_stock.stock.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails {

    private int code;
    private String status;
    private String message;
    private String details;
    private LocalDateTime timestamp;
}
