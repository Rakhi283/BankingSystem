package com.kalolytic.accountService.AccountService.client;

import com.kalolytic.accountService.AccountService.config.FeignClientConfig;
import com.kalolytic.commonModel.CommonModel.DTO.CustomerDTO;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="CUSTOMERSERVICE",configuration = FeignClientConfig.class)
public interface CustomerClients {

    @GetMapping("/api/customer/{id}")
    ResponseStructure<CustomerDTO> getCustomer(@PathVariable UUID id);

    @GetMapping("/api/customer/exist/{UUID}")
    boolean existCustomer(@PathVariable UUID UUID);
}
