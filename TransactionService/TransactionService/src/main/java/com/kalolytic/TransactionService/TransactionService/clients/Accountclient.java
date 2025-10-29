package com.kalolytic.TransactionService.TransactionService.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="AccountService", url="http://localhost:8081")
public interface Accountclient {

    @GetMapping("/account/{accountId}/customer/{customerId}")
       boolean accountBelongsToCustomer(
            @PathVariable("accountId") UUID accountId,
            @PathVariable("customerId") UUID customerId);
}
