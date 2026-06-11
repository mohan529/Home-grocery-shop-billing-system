package com.home.shop.ORDER_SERVICE.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

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
}
