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
    public PageData<Product> getCategoriesByPage(int page, int size, String order, String sortProperty) {
        Page<ProductEntity> productEntityPage = null;
        Pageable sortedPageable;

        if (sortProperty.equals(CATEGORIES)){
            sortedPageable = PageRequest.of(page, size);
            if (order.equals(ASC))
                productEntityPage = productRepository.findProductsSortedByCategoryAsc(sortedPageable);

            if (order.equals(DESC))
                productEntityPage = productRepository.findProductsSortedByCategoryDesc(sortedPageable);
        } else {
            String auxSortProperty = sortProperty.equals(BRAND) ? BRAND_NAME : sortProperty;
            sortedPageable = createPageRequest(page, size, order, auxSortProperty);

            productEntityPage = productRepository.findAll(sortedPageable);
        }

        assert productEntityPage != null;
        return productMapper.toPageData(productEntityPage);
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
    public List<String> getListCategoriesOfProducts(List<Long> products) {
        List<ProductEntity> productList = productRepository.findAllById(products);

        return productList.stream()
                .flatMap(product -> product.getCategories().stream()
                        .map(CategoryEntity::getName))
                .toList();
    }

    private Pageable createPageRequest(Integer page, Integer size, String order, String sortProperty) {
        Sort.Direction direction = Sort.Direction.fromString(order);
        return PageRequest.of(page, size, Sort.by(direction, sortProperty));
    }

    private boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    private Page<ProductEntity> filterProducts(String category, String brand, List<Long> products, Pageable pageable) {
        if (isNotBlank(category) && isNotBlank(brand)) {
            return productRepository.findByCategoryAndBrandAndIdIn(category, brand, products, pageable);
        } else if (isNotBlank(category)) {
            return productRepository.findByCategoryAndIdIn(category, products, pageable);
        } else if (isNotBlank(brand)) {
            return productRepository.findByBrandAndIdIn(brand, products, pageable);
        } else {
            return productRepository.findByIdIn(products, pageable);
        }
    }

    @Override
    public PageData<Product> getProductsByPageAndIds(
            Integer page, Integer size, String order, String category, String brand, List<Long> products) {
        Pageable sortedPageable = createPageRequest(page, size, order, NAME);
        Page<ProductEntity> productEntityPage = filterProducts(category, brand, products, sortedPageable);

        return productMapper.toPageData(productEntityPage);
    }

    @Override
    public List<Product> getAllProductsByIds(List<Long> products) {
        return productMapper.toListProduct(productRepository.findAllById(products));
    }
}
