package com.api_stock.stock.brand.infra.out;

import com.api_stock.stock.brand.domain.model.Brand;
import com.api_stock.stock.brand.domain.spi.IBrandPersistencePort;

public class BrandAdapter implements IBrandPersistencePort {

    private final IBrandRepository repository;
    private final IBrandMapper mapper;

    public BrandAdapter(IBrandRepository repository, IBrandMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void createBrand(Brand brand) {
        repository.save(mapper.toEntity(brand));
    }
}
