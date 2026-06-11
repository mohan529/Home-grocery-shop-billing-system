package com.home.shop.AUTH_SERVICE.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    private static final String jwtSecret =
            "super-secret-key-super-secret-key-super-secret-key"; // must be long enough

    private final long jwtExpirationMs = 60 * 60 * 1000;
    private final long refreshExpirationMs = 7 * 24 * 60 * 60 * 1000;

    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public String generateToken(String username, Set<String> roles, boolean refresh) {
        long expiry = refresh ? refreshExpirationMs : jwtExpirationMs;

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiry))
                .signWith(key)   // ✅ new API
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


