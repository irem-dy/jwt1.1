package com.jwtexample.jwt11.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "my_secret_key"; // Gerçek projede .env ya da config'den alınmalı

    // Token üretme
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("jwt11-app")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 saat
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Token'dan username çıkarma
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Token geçerlilik kontrolü
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    // Expiration kontrolü
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Token içindeki claim'leri çıkar
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
