package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.page.PageData;
import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;
import com.api_stock.stock.domain.product.util.ProductConstants;
import com.api_stock.stock.domain.util.GlobalConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

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

        if (sortProperty.equals(ProductConstants.CATEGORIES)){
            sortedPageable = PageRequest.of(page, size);
            if (sortDirection.equals(GlobalConstants.ASC))
                productEntityPage = productRepository.findProductsSortedByCategoryAsc(sortedPageable);

            if (sortDirection.equals(GlobalConstants.DESC))
                productEntityPage = productRepository.findProductsSortedByCategoryDesc(sortedPageable);
        } else {
            Sort.Direction direction = Sort.Direction.fromString(sortDirection);
            String auxSortProperty = sortProperty.equals(ProductConstants.BRAND) ? ProductConstants.BRAND_NAME : sortProperty;
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
}
