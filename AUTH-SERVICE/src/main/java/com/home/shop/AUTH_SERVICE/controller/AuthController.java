package com.home.shop.AUTH_SERVICE.controller;

import com.home.shop.AUTH_SERVICE.config.JwtUtil;
import com.home.shop.AUTH_SERVICE.dto.AuthRequest;
import com.home.shop.AUTH_SERVICE.dto.AuthResponse;
import com.home.shop.AUTH_SERVICE.dto.RegisterRequest;
import com.home.shop.AUTH_SERVICE.dto.TokenValidationResponse;
import com.home.shop.AUTH_SERVICE.entity.Role;
import com.home.shop.AUTH_SERVICE.serviceImpl.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validate(
            @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");
        boolean valid = jwtUtil.validateToken(token);

        return ResponseEntity.ok(new TokenValidationResponse(valid));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> addRole(@RequestBody Role role) {
        return ResponseEntity.ok(authService.addRole(role));
    }
}
