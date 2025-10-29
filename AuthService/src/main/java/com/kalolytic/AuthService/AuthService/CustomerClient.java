package com.kalolytic.AuthService.AuthService;

import com.kalolytic.customerService.CustomerService.DTO.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CUSTOMER-SERVICE")
    public interface CustomerClient {

        @GetMapping("/customer/auth/validate")
        CustomerDTO validate(
                @RequestParam String email,
                @RequestParam String password);
    }


