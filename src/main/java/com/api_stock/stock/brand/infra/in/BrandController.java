package com.api_stock.stock.brand.infra.in;

import com.api_stock.stock.brand.app.dto.BrandRequest;
import com.api_stock.stock.brand.app.handler.IBrandHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
