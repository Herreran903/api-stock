package com.api_stock.stock.infra.product.out;

import com.api_stock.stock.domain.product.model.Product;
import com.api_stock.stock.domain.product.spi.IProductPersistencePort;

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
}
