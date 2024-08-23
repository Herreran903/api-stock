package com.api_stock.stock.category.app.dto;

import com.api_stock.stock.brand.domain.util.Constants;
import com.api_stock.stock.category.domain.exception.ExceptionMessage;
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

    @NotNull(message = ExceptionMessage.EMPTY_NAME)
    @NotEmpty(message = ExceptionMessage.EMPTY_NAME)
    @Size(max = Constants.MAX_NAME_LENGTH,
            message = ExceptionMessage.TOO_LONG_NAME)
    private String name;

    @NotNull(message = ExceptionMessage.EMPTY_DESCRIPTION)
    @NotEmpty(message = ExceptionMessage.EMPTY_DESCRIPTION)
    @Size(max = Constants.MAX_DESCRIPTION_LENGTH,
            message = ExceptionMessage.TOO_LONG_DESCRIPTION)
    private String description;
}
