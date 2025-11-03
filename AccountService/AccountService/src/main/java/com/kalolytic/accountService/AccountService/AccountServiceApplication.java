package com.kalolytic.accountService.AccountService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = { "com.kalolytic.accountService",
        "com.kalolytic.commonModel"})
@EnableCaching
@EnableFeignClients
public class AccountServiceApplication {

	public static void main(String[] args) {

        SpringApplication.run(AccountServiceApplication.class, args);



	}

}
