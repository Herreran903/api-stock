package com.api_stock.stock.infra.category.in;

import com.api_stock.stock.app.category.dto.CategoryRequest;
import com.api_stock.stock.app.category.dto.CategoryResponse;
import com.api_stock.stock.app.category.handler.ICategoryHandler;
import com.api_stock.stock.domain.page.PageData;
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

@RestController
@RequestMapping("/categories")
@Validated
public class CategoryController {

    private final ICategoryHandler categoryHandler;

    public CategoryController(ICategoryHandler categoryHandler) {
        this.categoryHandler = categoryHandler;
    }

    @Operation(
            summary = "Create new category",
            description = "This endpoint allows you to create a new category by providing a category name and description.",
            parameters = {
                    @Parameter(name = "name", description = "Name of category", required = true, schema = @Schema(implementation = String.class)),
                    @Parameter(name = "description", description = "Description of category", required = true, schema = @Schema(implementation = String.class))
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = CategoryRequest.class),
                            examples = {@ExampleObject(value = "{\"name\":\"Electronics\",\"description\":\"Devices and gadgets\"}")}
                    )
            )
    )
    @PostMapping("/")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        categoryHandler.createBrand(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get paginated list of categories",
            description = "This endpoint allows you to retrieve a paginated list of categories with optional sorting. " +
                    "You can specify the page number, page size, and sorting direction.",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve", schema = @Schema(implementation = int.class)),
                    @Parameter(name = "size", description = "Number of categories per page", schema = @Schema(implementation = int.class)),
                    @Parameter(name = "sortDirection", description = "Sort direction (ASC or DESC)", schema = @Schema(implementation = String.class))
            }
    )
    @GetMapping("/")
    public ResponseEntity<PageData<CategoryResponse>> getCategoriesByPage(
            @Min(value = GlobalConstants.MIN_PAGE_NUMBER, message = GlobalExceptionMessage.NO_NEGATIVE_PAGE)
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_NUMBER)
            int page,
            @Min(value = GlobalConstants.MIN_PAGE_SIZE, message = GlobalExceptionMessage.GREATER_ZERO_SIZE)
            @RequestParam(defaultValue = GlobalConstants.DEFAULT_PAGE_SIZE)
            int size,
            @Pattern(regexp = GlobalConstants.ORDER_REGEX, message = GlobalExceptionMessage.INVALID_SORT_DIRECTION)
            @RequestParam(defaultValue = GlobalConstants.ASC)
            String sortDirection) {
        PageData<CategoryResponse> categories = categoryHandler.getCategoriesByPage(page, size, sortDirection);

        return ResponseEntity.ok(categories);
    }
}
