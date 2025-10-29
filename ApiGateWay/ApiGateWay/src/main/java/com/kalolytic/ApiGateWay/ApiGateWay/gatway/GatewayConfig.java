package com.kalolytic.ApiGateWay.ApiGateWay.gatway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

@Bean
    RouteLocator  routeLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("AccountService", r->r.path("/account/**")
                    .filters(f->f.stripPrefix(1))
                    .uri("lb://AccountService"))
            .route("CustomerService", r->r.path("/api/cutomer/**")
                    .filters(f->f.stripPrefix(1))
                    .uri("lb://CustomerService"))
            .route("TransactionService",r->r.path("/api/transactions/**")
                    .filters(f->f.stripPrefix(1))
                    .uri("lb://TransactionService"))
            .build();
}
}
