package com.kalolytic.TransactionService.TransactionService.clients;

import com.kalolytic.TransactionService.TransactionService.config.FeignClientConfig;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="CUSTOMERSERVICE",configuration = FeignClientConfig.class)
public interface Customer {

    @GetMapping("/api/customer/exists/{UUID}")
    ResponseStructure<Boolean> existCustomer(@PathVariable UUID UUID);
}
