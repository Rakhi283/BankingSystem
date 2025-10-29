package com.kalolytic.AuthService.AuthService;

import com.kalolytic.customerService.CustomerService.DTO.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomerClient customerClient;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        CustomerDTO customer = customerClient.validate(request.getEmail(), request.getPassword());

        String token = jwtService.generateToken(customer.getEmail());

        return ResponseEntity.ok(new AuthResponse(token, customer.getEmail()));
    }
}
