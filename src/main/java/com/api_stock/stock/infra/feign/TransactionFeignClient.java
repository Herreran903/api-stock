package com.api_stock.stock.infra.feign;

import com.api_stock.stock.app.product.dto.SupplyRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${transaction.service.name}", url = "${transaction.service.url}", configuration = FeignClientInterceptor.class)
public interface TransactionFeignClient {
    @PostMapping("${transaction.service.url.create}")
    Integer createSupply(@Valid @RequestBody SupplyRequest supplyRequest);
}
