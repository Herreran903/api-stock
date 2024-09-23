package com.api_stock.stock.infra.product.in;

import com.api_stock.stock.app.product.dto.*;
import com.api_stock.stock.app.product.handler.IProductHandler;
import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.exception.ProductExceptionMessage;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalConstants;
import com.api_stock.stock.domain.util.GlobalExceptionMessage;
import com.api_stock.stock.infra.exception.ExceptionDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.api_stock.stock.infra.product.util.ProductSwaggerMessages.*;
import static com.api_stock.stock.infra.util.SwaggerMessages.*;
import static com.api_stock.stock.infra.util.Urls.PRODUCT_URL;

@RestController
@RequestMapping(PRODUCT_URL)
@Validated
public class ProductController {
    private static final String CREATE_PRODUCT_URL = "/create";
    private static final String FETCH_PRODUCT_URL = "/fetch";
    private static final String INCREASE_STOCK_URL = "/increase";
    private static final String GET_CATEGORIES_URL = "/categories";
    private static final String GET_STOCK_URL = "/stock";

    private final IProductHandler productHandler;

    public ProductController(IProductHandler productHandler) {
        this.productHandler = productHandler;
    }

    @Operation(
            summary = "Create new product",
            description = "Allows an admin user to create a new product by providing the necessary details such as name, description, price, stock, brand, and associated categories",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product creation request body",
                    content = @Content(
                            schema = @Schema(implementation = ProductRequest.class),
                            examples = {@ExampleObject(value = CREATE_PRODUCT_REQUEST_EXAMPLE)
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_201, description = RESPONSE_201_DESCRIPTION),
            @ApiResponse(responseCode = CODE_400, description = RESPONSE_400_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            ),
            @ApiResponse(responseCode = CODE_500, description = RESPONSE_500_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            )
    })
    @PostMapping(CREATE_PRODUCT_URL)
    @PreAuthorize("hasRole(T(com.api_stock.stock.domain.role.util.RoleEnum).ROLE_ADMIN.toString())")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        productHandler.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get paginated list of products",
            description = "Retrieve a paginated list of products with optional sorting by specifying page number, page size, sorting direction (ASC or DESC), and sorting property (name, brand, or categories)",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve (starting from 0)",
                            schema = @Schema(implementation = int.class)),
                    @Parameter(name = "size", description = "Number of products per page",
                            schema = @Schema(implementation = int.class)),
                    @Parameter(name = "sortDirection", description = "Sorting direction (ASC for ascending or DESC for descending)",
                            schema = @Schema(implementation = String.class)),
                    @Parameter(name = "sortProperty", description = "Property to sort by (e.g., name, brand, or categories)",
                            schema = @Schema(implementation = String.class))
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_200, description = RESPONSE_200_DESCRIPTION),
            @ApiResponse(responseCode = CODE_400, description = RESPONSE_400_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            ),
            @ApiResponse(responseCode = CODE_500, description = RESPONSE_500_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            )
    })
    @GetMapping(FETCH_PRODUCT_URL)
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

    @Operation(
            summary = "Increase product stock",
            description = "Allows a warehouse assistant to increase the stock of a product by providing the product ID and the quantity to be added",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body to increase product stock",
                    content = @Content(
                            schema = @Schema(implementation = ProductRequest.class),
                            examples = {
                                    @ExampleObject(value = INCREASE_STOCK_REQUEST_EXAMPLE)
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_204, description = RESPONSE_204_DESCRIPTION),
            @ApiResponse(responseCode = CODE_400, description = RESPONSE_400_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            ),
            @ApiResponse(responseCode = CODE_500, description = RESPONSE_500_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            )
    })
    @PostMapping(INCREASE_STOCK_URL)
    @PreAuthorize("hasRole(T(com.api_stock.stock.domain.role.util.RoleEnum).ROLE_WAREHOUSE_ASSISTANT.toString())")
    public ResponseEntity<Void> updateStock(@Valid @RequestBody StockRequest stockRequest) {
        productHandler.updateStock(stockRequest);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get categories of products",
            description = "Allows a client to retrieve the categories associated with a list of products by providing product IDs.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body with product IDs",
                    content = @Content(
                            schema = @Schema(implementation = ProductIdListRequest.class),
                            examples = {
                                    @ExampleObject(value = GET_CATEGORIES_REQUEST_EXAMPLE)
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_200, description = RESPONSE_200_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = CategoryIdListResponse.class))
            ),
            @ApiResponse(responseCode = CODE_400, description = RESPONSE_400_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            ),
            @ApiResponse(responseCode = CODE_500, description = RESPONSE_500_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            )
    })
    @PostMapping(GET_CATEGORIES_URL)
    @PreAuthorize("hasRole(T(com.api_stock.stock.domain.role.util.RoleEnum).ROLE_CLIENT.toString())")
    public ResponseEntity<CategoryIdListResponse> getListCategoriesOfProducts(@Valid @RequestBody ProductIdListRequest productIdListRequest) {
        CategoryIdListResponse categoryIdListResponse = productHandler.getListCategoriesOfProducts(productIdListRequest);

        return ResponseEntity.ok(categoryIdListResponse);
    }

    @Operation(
            summary = "Get product stock",
            description = "Allows a client to retrieve the stock level of a specific product by providing the product ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body with product ID",
                    content = @Content(
                            schema = @Schema(implementation = ProductIdRequest.class),
                            examples = {
                                    @ExampleObject(value = GET_STOCK_REQUEST_EXAMPLE)
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CODE_200, description = RESPONSE_200_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = Integer.class))
            ),
            @ApiResponse(responseCode = CODE_400, description = RESPONSE_400_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            ),
            @ApiResponse(responseCode = CODE_500, description = RESPONSE_500_DESCRIPTION,
                    content = @Content(schema = @Schema(implementation = ExceptionDetails.class))
            )
    })
    @PostMapping(GET_STOCK_URL)
    @PreAuthorize("hasRole(T(com.api_stock.stock.domain.role.util.RoleEnum).ROLE_CLIENT.toString())")
    public ResponseEntity<Integer> getStockOfProduct(@Valid @RequestBody ProductIdRequest productIdRequest) {
        Integer stock = productHandler.getStockOfProduct(productIdRequest);

        return ResponseEntity.ok(stock);
    }
}
