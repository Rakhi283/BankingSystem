package com.kalolytic.TransactionService.TransactionService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="customerService", url="http://localhost:8080")
public interface customer {

    @GetMapping("/customer/exist/{UUID}")
   boolean existCustomer(@PathVariable UUID UUID);
}
