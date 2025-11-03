package com.kalolytic.AuthService.AuthService.service;


import com.kalolytic.AuthService.AuthService.model.AuthResponse;
import com.kalolytic.AuthService.AuthService.model.LoginRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthServiceImpl {

    private final JwtService jwtService;
    private final WebClient.Builder webClientBuilder;

    public AuthServiceImpl(JwtService jwtService, WebClient.Builder webClientBuilder) {
        this.jwtService = jwtService;
        this.webClientBuilder = webClientBuilder;
    }

    public AuthResponse login(LoginRequest request) {
        // Check user exists in Customer Service
        Boolean exists = webClientBuilder.build()
                .get()
                .uri("http://CUSTOMER-SERVICE/api/customer/exists/" + request.getEmail())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (Boolean.TRUE.equals(exists)) {
            String token = jwtService.generateToken(request.getEmail());
            return new AuthResponse(token, request.getEmail(), "Login successful!");
        } else {
            return new AuthResponse(null, request.getEmail(), "Invalid credentials!");
        }
    }
}


