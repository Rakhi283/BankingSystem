package com.kalolytic.AuthService.AuthService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService{

        private static final String SECRET = "yourSecretKey123";

        public String generateToken(String email){
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();
        }
    }


