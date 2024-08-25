package com.api_stock.stock.infra.brand.in;

import com.api_stock.stock.app.brand.dto.BrandRequest;
import com.api_stock.stock.app.brand.dto.BrandResponse;
import com.api_stock.stock.app.brand.handler.IBrandHandler;
import com.api_stock.stock.domain.brand.exception.BrandExceptionMessage;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.util.GlobalConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@Validated
public class BrandController {

    private final IBrandHandler brandHandler;

    public BrandController(IBrandHandler brandHandler) {
        this.brandHandler = brandHandler;
    }

    @Operation(
            summary = "Create new brand",
            description = "This endpoint allows you to create a new brand by providing a brand name and description.",
            parameters = {
                    @Parameter(name = "name", description = "Name of brand", required = true, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "description", description = "Description of brand", required = true, schema = @Schema(implementation = String.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = BrandRequest.class),
                            examples = {@ExampleObject(value = "{\"name\":\"Nike\",\"description\":\"High quality clothing\"}")}
                    )
            )
    )
    @PostMapping("/")
    ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        brandHandler.createBrand(brandRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get paginated list of brands",
            description = "This endpoint allows you to retrieve a paginated list of brands with optional sorting. " +
                    "You can specify the page number, page size, and sorting direction.",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve", schema = @Schema(implementation = Integer.class)),
                    @Parameter(name = "size", description = "Number of categories per page", schema = @Schema(implementation = Integer.class)),
                    @Parameter(name = "sortDirection", description = "Sort direction (ASC or DESC)", schema = @Schema(implementation = String.class))
            }
    )
    @GetMapping("/")
    ResponseEntity<PageData<BrandResponse>> getBrandsByPage(
            @Min(value = GlobalConstants.MIN_PAGE_NUMBER, message = BrandExceptionMessage.NO_NEGATIVE_PAGE)
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_NUMBER)
            int page,
            @Min(value = GlobalConstants.MIN_PAGE_SIZE, message = BrandExceptionMessage.GREATER_ZERO_SIZE)
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_SIZE)
            int size,
            @Pattern(regexp = GlobalConstants.ORDER_REGEX, message = BrandExceptionMessage.INVALID_SORT_DIRECTION)
            @RequestParam(defaultValue = GlobalConstants.ASC)
            String sortDirection) {
        PageData<BrandResponse> brands = brandHandler.getBrandsByPage(page, size, sortDirection);

        return ResponseEntity.ok(brands);
    }
}
