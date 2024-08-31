package com.api_stock.stock.infra.product.in;

import com.api_stock.stock.app.product.dto.ProductRequest;
import com.api_stock.stock.app.product.dto.ProductResponse;
import com.api_stock.stock.app.product.handler.IProductHandler;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@Validated
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
                            schema = @Schema(implementation = int.class)),
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
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get paginated list of products",
            description = "This endpoint allows you to retrieve a paginated list of products with optional sorting. " +
                    "You can specify the page number, page size, sorting direction and sorting property.",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve",
                            schema = @Schema(implementation = int.class)),
                    @Parameter(name = "size", description = "Number of products per page",
                            schema = @Schema(implementation = int.class)),
                    @Parameter(name = "sortDirection", description = "Sort direction (ASC or DESC)",
                            schema = @Schema(implementation = String.class)),
                    @Parameter(name = "sortProperty", description = "Sort property (name or brand or categories)",
                            schema = @Schema(implementation = String.class))
            }
    )
    @GetMapping("/")
    public ResponseEntity<PageData<ProductResponse>> getProductsByPage(
            @Min(value = GlobalConstants.MIN_PAGE_NUMBER, message = GlobalExceptionMessage.NO_NEGATIVE_PAGE)
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_NUMBER)
            int page,
            @Min(value = GlobalConstants.MIN_PAGE_SIZE, message = GlobalExceptionMessage.GREATER_ZERO_SIZE)
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_SIZE)
            int size,
            @Pattern(regexp = GlobalConstants.ORDER_REGEX, message = GlobalExceptionMessage.INVALID_SORT_DIRECTION)
            @RequestParam(defaultValue = GlobalConstants.ASC)
            String sortDirection,
            @Pattern(regexp = ProductConstants.PROPERTY_REGEX, message = ProductExceptionMessage.INVALID_PROPERTY_DIRECTION)
            @RequestParam(defaultValue = ProductConstants.NAME)
            String sortProperty) {
        PageData<ProductResponse> categories = productHandler.getProductsByPage(page, size, sortDirection, sortProperty);

        return ResponseEntity.ok(categories);
    }
}
