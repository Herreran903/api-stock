package com.api_stock.stock.infra.product.in;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.handler.IProductHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductHandler productHandler;

    public ProductController(IProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @Operation(
            summary = "Create new product",
            description = "This endpoint allows you to create a new product by providing a " +
                    "product name, description, price, stock, brand, and associated categories.",
            parameters = {
                    @Parameter(name = "name", description = "Name of the product", required = true,
                            schema = @Schema(implementation = String.class)),
                    @Parameter(name = "description", description = "Description of the product",
                            schema = @Schema(implementation = String.class)),
                    @Parameter(name = "price", description = "Price of the product", required = true,
                            schema = @Schema(implementation = BigDecimal.class)),
                    @Parameter(name = "stock", description = "Stock quantity of the product",
                            schema = @Schema(implementation = Integer.class)),
                    @Parameter(name = "brandId", description = "ID of the brand associated with the product", required = true,
                            schema = @Schema(implementation = Long.class)),
                    @Parameter(name = "categoryIds", description = "List of category IDs associated with the product", required = true,
                            schema = @Schema(implementation = List.class, subTypes = Long.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body to create a new product",
                    content = @Content(
                            schema = @Schema(implementation = ProductRequest.class),
                            examples = {
                                    @ExampleObject(value =
                                            "{\"name\":\"Smartphone\",\"" +
                                            "description\":\"Latest model smartphone\"," +
                                            "\"price\":699.99," +
                                            "\"stock\":50," +
                                            "\"brandId\":1," +
                                            "\"categoryIds\":[1,2,3]}")
                            }
                    )
            )
    )
    @PostMapping("/")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productHandler.createProduct(productRequest);
        return ResponseEntity.ok().build();
    }
}
