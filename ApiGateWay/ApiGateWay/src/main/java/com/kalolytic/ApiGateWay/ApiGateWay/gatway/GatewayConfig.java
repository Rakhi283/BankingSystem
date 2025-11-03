package com.kalolytic.ApiGateWay.ApiGateWay.gatway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter; // ✅ Inject filter

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // ✅ Allow /auth/** without token
                .route("AuthService", r -> r.path("/auth/**")
                        .uri("lb://AuthService"))

                // ✅ JWT Required for AccountService
                .route("AccountService", r -> r.path("/account/**")
                        .filters(f -> f.stripPrefix(1).filter(jwtAuthFilter))
                        .uri("lb://AccountService"))

                // ✅ JWT Required for CustomerService
                .route("CustomerService", r -> r.path("/api/customer/**")
                        .filters(f -> f.stripPrefix(1).filter(jwtAuthFilter))
                        .uri("lb://CustomerService"))

                // ✅ JWT Required for TransactionService
                .route("TransactionService", r -> r.path("/api/transactions/**")
                        .filters(f -> f.stripPrefix(1).filter(jwtAuthFilter))
                        .uri("lb://TransactionService"))

                .build();
    }
}
