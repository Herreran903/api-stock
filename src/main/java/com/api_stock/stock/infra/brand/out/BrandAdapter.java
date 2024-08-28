package com.api_stock.stock.infra.brand.out;

import com.api_stock.stock.domain.brand.model.Brand;
import com.api_stock.stock.domain.brand.spi.IBrandPersistencePort;
import com.api_stock.stock.domain.page.PageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Boolean isBrandPresentByName(String brandName) {
        return repository.findByName(brandName).isPresent();
    }

    @Override
    public PageData<Brand> getBrandsByPage(int page, int size, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(direction, "name"));

        Page<BrandEntity> brandEntityPage = repository.findAll(sortedPageable);

        List<Brand> brands = mapper.toBrandsList(brandEntityPage.getContent());

        return new PageData<>(
                brands,
                brandEntityPage.getNumber(),
                (int) brandEntityPage.getTotalElements(),
                brandEntityPage.isFirst(),
                brandEntityPage.isLast(),
                brandEntityPage.hasNext(),
                brandEntityPage.hasPrevious()
        );
    }

    @Override
    public Optional<Brand> getBrandById(Long brandId) {
        return repository.findById(brandId).map(mapper::toBrand);
    }

}
