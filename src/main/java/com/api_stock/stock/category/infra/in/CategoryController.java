package com.api_stock.stock.category.infra.in;

import com.api_stock.stock.category.app.dto.CategoryRequest;
import com.api_stock.stock.category.app.dto.CategoryResponse;
import com.api_stock.stock.category.app.handler.ICategoryHandler;
import com.api_stock.stock.category.domain.model.CategoryPage;
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
@RequestMapping("/categories")
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
        categoryHandler.createCategory(categoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Get paginated list of categories",
            description = "This endpoint allows you to retrieve a paginated list of categories with optional sorting. " +
                    "You can specify the page number, page size, and sorting direction.",
            parameters = {
                    @Parameter(name = "page", description = "Page number to retrieve", schema = @Schema(implementation = Integer.class)),
                    @Parameter(name = "size", description = "Number of categories per page", schema = @Schema(implementation = Integer.class)),
                    @Parameter(name = "sortDirection", description = "Sort direction (ASC or DESC)", schema = @Schema(implementation = String.class))
            }
    )
    @GetMapping("/")
    public ResponseEntity<CategoryPage<CategoryResponse>> getCategoriesByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        CategoryPage<CategoryResponse> categories = categoryHandler.getCategoriesByPage(page, size, sortDirection);

        return ResponseEntity.ok(categories);
    }
}
