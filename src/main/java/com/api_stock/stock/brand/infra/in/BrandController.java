package com.api_stock.stock.brand.infra.in;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.app.handler.IBrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
