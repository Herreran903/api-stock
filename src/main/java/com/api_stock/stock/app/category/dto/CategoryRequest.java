package com.api_stock.stock.app.category.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.api_stock.stock.domain.category.exception.CategoryExceptionMessage.*;
import static com.api_stock.stock.domain.category.util.CategoryConstants.MAX_DESCRIPTION_LENGTH;
import static com.api_stock.stock.domain.category.util.CategoryConstants.MAX_NAME_LENGTH;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotNull(message = EMPTY_NAME)
    @NotEmpty(message = EMPTY_NAME)
    @Size(max = MAX_NAME_LENGTH, message = TOO_LONG_NAME)
    private String name;

    @NotNull(message = EMPTY_DESCRIPTION)
    @NotEmpty(message = EMPTY_DESCRIPTION)
    @Size(max = MAX_DESCRIPTION_LENGTH, message = TOO_LONG_DESCRIPTION)
    private String description;
}
