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

import static com.api_stock.stock.domain.brand.util.BrandConstants.NAME;

public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandMapper brandMapper;

    public BrandAdapter(IBrandRepository brandRepository, IBrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public void createBrand(Brand brand) {
        brandRepository.save(brandMapper.toEntity(brand));
    }

    @Override
    public Boolean isBrandPresentByName(String brandName) {
        return brandRepository.findByName(brandName).isPresent();
    }

    @Override
    public PageData<Brand> getBrandsByPage(int page, int size, String order) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        Pageable sortedPageable = PageRequest.of(page, size, Sort.by(direction, NAME));

        Page<BrandEntity> brandEntityPage = brandRepository.findAll(sortedPageable);

        List<Brand> brands = brandMapper.toBrandsList(brandEntityPage.getContent());

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
        return brandRepository.findById(brandId).map(brandMapper::toBrand);
    }

}
