package com.api_stock.stock.brand.infra.in;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.app.dto.BrandResponse;
import com.api_stock.stock.brand.app.handler.IBrandHandler;
import com.api_stock.stock.brand.domain.model.BrandPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
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
    ResponseEntity<BrandPage<BrandResponse>> getBrandsByPage(
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "10")
            int size,
            @Valid
            @RequestParam(defaultValue = "ASC")
            String sortDirection) {
        BrandPage<BrandResponse> brands = brandHandler.getBrandsByPage(page, size, sortDirection);

        return ResponseEntity.ok(brands);
    }
}
