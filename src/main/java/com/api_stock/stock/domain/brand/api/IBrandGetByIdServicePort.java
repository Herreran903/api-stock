package com.api_stock.stock.domain.brand.api;

import com.api_stock.stock.domain.brand.model.Brand;

public interface IBrandGetByIdServicePort {
    Brand getBrandById(Long id);
}
