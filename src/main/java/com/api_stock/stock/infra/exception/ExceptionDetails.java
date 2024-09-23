package com.api_stock.stock.infra.exception;

import com.api_stock.stock.domain.error.ErrorDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails {
    private int code;
    private String status;
    private String message;
    private String details;
    private LocalDateTime timestamp;
    private List<ErrorDetail> errors;
}
