package com.kalolytic.TransactionService.TransactionService.config;

import com.kalolytic.TransactionService.TransactionService.FeignExceptionHandler.FeignClientsEncoder;
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
