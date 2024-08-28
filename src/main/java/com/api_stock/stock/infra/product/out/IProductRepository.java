package com.api_stock.stock.infra.product.out;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
}
