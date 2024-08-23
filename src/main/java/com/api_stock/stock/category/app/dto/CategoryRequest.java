package com.api_stock.stock.category.app.dto;

import com.api_stock.stock.brand.domain.util.BrandConstants;
import com.api_stock.stock.category.domain.exception.CategoryExceptionMessage;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CategoryRequest {

    @NotNull(message = CategoryExceptionMessage.EMPTY_NAME)
    @NotEmpty(message = CategoryExceptionMessage.EMPTY_NAME)
    @Size(max = BrandConstants.MAX_NAME_LENGTH,
            message = CategoryExceptionMessage.TOO_LONG_NAME)
    private String name;

    @NotNull(message = CategoryExceptionMessage.EMPTY_DESCRIPTION)
    @NotEmpty(message = CategoryExceptionMessage.EMPTY_DESCRIPTION)
    @Size(max = BrandConstants.MAX_DESCRIPTION_LENGTH,
            message = CategoryExceptionMessage.TOO_LONG_DESCRIPTION)
    private String description;
}
