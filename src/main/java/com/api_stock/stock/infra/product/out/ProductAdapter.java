package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;
import com.api_stock.stock.infra.category.out.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static com.api_stock.stock.domain.product.util.ProductConstants.*;
import static com.api_stock.stock.domain.util.GlobalConstants.ASC;
import static com.api_stock.stock.domain.util.GlobalConstants.DESC;

public class ProductAdapter implements IProductPersistencePort {
    private final IProductRepository productRepository;
    private final IProductMapper productMapper;

    public ProductAdapter(IProductRepository productRepository, IProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(productMapper.toEntity(product));
    }

    @Override
    public PageData<Product> getCategoriesByPage(int page, int size, String sortDirection, String sortProperty) {
        Page<ProductEntity> productEntityPage = null;
        Pageable sortedPageable;

        if (sortProperty.equals(CATEGORIES)){
            sortedPageable = PageRequest.of(page, size);
            if (sortDirection.equals(ASC))
                productEntityPage = productRepository.findProductsSortedByCategoryAsc(sortedPageable);

            if (sortDirection.equals(DESC))
                productEntityPage = productRepository.findProductsSortedByCategoryDesc(sortedPageable);
        } else {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection);
            String auxSortProperty = sortProperty.equals(BRAND) ? BRAND_NAME : sortProperty;
            sortedPageable = PageRequest.of(page, size, Sort.by(direction, auxSortProperty));

            productEntityPage = productRepository.findAll(sortedPageable);
        }

        assert productEntityPage != null;
        List<Product> products = productMapper.toListProduct(productEntityPage.getContent());

        return new PageData<>(
                products,
                productEntityPage.getNumber(),
                (int) productEntityPage.getTotalElements(),
                productEntityPage.isFirst(),
                productEntityPage.isLast(),
                productEntityPage.hasNext(),
                productEntityPage.hasPrevious()
        );
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id).map(productMapper::toProduct);
    }

    @Override
    public void updateProduct(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);

        productRepository.save(productEntity);
    }

    @Override
    public List<String> getListCategoriesOfProducts(List<Long> productIds) {
        List<ProductEntity> products = productRepository.findAllById(productIds);

        return products.stream()
                .flatMap(product -> product.getCategories().stream()
                        .map(CategoryEntity::getName))
                .toList();
    }
}
