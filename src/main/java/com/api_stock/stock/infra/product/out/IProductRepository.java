package com.api_stock.stock.infra.product.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query
    Page<ProductEntity> findProductsSortedByCategoryAsc(Pageable pageable);

    @Query
    Page<ProductEntity> findProductsSortedByCategoryDesc(Pageable pageable);

}
