package com.kalolytic.accountService.AccountService.config;


import com.kalolytic.accountService.AccountService.ExceptionHandler.FeignClientsEncoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientsEncoder();
    }
}

