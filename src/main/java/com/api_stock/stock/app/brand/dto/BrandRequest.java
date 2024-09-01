package com.api_stock.stock.app.brand.dto;

import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.brand.util.BrandConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {

    @NotNull(message = BrandExceptionMessage.EMPTY_NAME)
    @NotEmpty(message = BrandExceptionMessage.EMPTY_NAME)
    @Size(max = BrandConstants.MAX_NAME_LENGTH,
            message = BrandExceptionMessage.TOO_LONG_NAME)
    private String name;

    @NotNull(message = BrandExceptionMessage.EMPTY_DESCRIPTION)
    @NotEmpty(message = BrandExceptionMessage.EMPTY_DESCRIPTION)
    @Size(max = BrandConstants.MAX_DESCRIPTION_LENGTH,
            message = BrandExceptionMessage.TOO_LONG_DESCRIPTION)
    private String description;
}
