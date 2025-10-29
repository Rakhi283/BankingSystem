package com.kalolytic.AuthService.AuthService;

import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String generateToken(String email);
}
