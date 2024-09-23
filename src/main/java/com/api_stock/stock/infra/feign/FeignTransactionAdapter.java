package com.api_stock.stock.infra.feign;

import com.api_stock.stock.domain.product.spi.IFeignTransactionAdapterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeignTransactionAdapter implements IFeignTransactionAdapterPort {
    private final TransactionFeignClient transactionFeignClient;

    @Override
    public void createSupply(Long id){
//        SupplyRequest productIdRequest = new SupplyRequest(id);
//        supplyFeignClient.createSupply(productIdRequest);
    }
}
