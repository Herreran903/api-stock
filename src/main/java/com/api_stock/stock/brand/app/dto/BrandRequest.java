package com.api_stock.stock.brand.app.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BrandRequest {

    @NotNull(message = "")
    @NotEmpty(message = "")
    @Size(min = 1, max = 50, message = "")
    private String name;

    @NotNull
    @NotEmpty(message = "")
    @Size(min = 1, max = 120, message = "")
    private String description;
}
