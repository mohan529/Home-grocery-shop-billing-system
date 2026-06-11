package com.home.shop.API_GATEWAY.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

@Component
public class JwtUtil {

    private static final String jwtSecret =
            "super-secret-key-super-secret-key-super-secret-key"; // must be long enough
    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getRoles(String token) {
        Claims claims = getClaims(token);
        return claims.get("roles", List.class);
    }

    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

