package com.kalolytic.AuthService.AuthService.service;

import com.kalolytic.AuthService.AuthService.model.AuthResponse;
import com.kalolytic.AuthService.AuthService.model.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthResponse login(LoginRequest request);
}
