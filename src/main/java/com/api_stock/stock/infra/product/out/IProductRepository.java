package com.api_stock.stock.infra.product.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.api_stock.stock.domain.product.util.ProductConstants.*;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query
    Page<ProductEntity> findProductsSortedByCategoryAsc(Pageable pageable);

    @Query
    Page<ProductEntity> findProductsSortedByCategoryDesc(Pageable pageable);

    @Query
    Page<ProductEntity> findByCategoryAndBrandAndIdIn(
            @Param(CATEGORY) String categoryName,
            @Param(BRAND) String brandName,
            @Param(PRODUCTS) List<Long> productIds,
            Pageable pageable);

    @Query
    Page<ProductEntity> findByCategoryAndIdIn(
            @Param(CATEGORY) String categoryName,
            @Param(PRODUCTS) List<Long> productIds,
            Pageable pageable);

    @Query
    Page<ProductEntity> findByBrandAndIdIn(
            @Param(BRAND) String brandName,
            @Param(PRODUCTS) List<Long> productIds,
            Pageable pageable);

    @Query
    Page<ProductEntity> findByIdIn(
            @Param(PRODUCTS) List<Long> productIds,
            Pageable pageable);
}
