package com.api_stock.stock.domain.product.spi;

public interface IFeignTransactionAdapterPort {
    void createSupply(Long productId);
}
