package com.home.shop.AUTH_SERVICE.serviceImpl;


import com.home.shop.AUTH_SERVICE.config.JwtUtil;
import com.home.shop.AUTH_SERVICE.dto.AuthRequest;
import com.home.shop.AUTH_SERVICE.dto.AuthResponse;
import com.home.shop.AUTH_SERVICE.dto.RegisterRequest;
import com.home.shop.AUTH_SERVICE.entity.Role;
import com.home.shop.AUTH_SERVICE.entity.UserCredentials;
import com.home.shop.AUTH_SERVICE.repository.RoleRepository;
import com.home.shop.AUTH_SERVICE.repository.UserCredentialsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserCredentialsRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthService(UserCredentialsRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }


        Role role = roleRepository.findByRoleName(request.getRole())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Invalid role: " + request.getRole()
                        ));


        UserCredentials user = new UserCredentials();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return login(new AuthRequest(request.getUsername(), request.getPassword()));
    }

    public AuthResponse login(AuthRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()));

        UserCredentials user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        Set<String> roles = user.getRoles().stream()
                .map(r -> r.getRoleName().name())                               //r.getRoleName().name
                .collect(Collectors.toSet());

        String accessToken = jwtUtil.generateToken(user.getUsername(), roles, false);
        String refreshToken = jwtUtil.generateToken(user.getUsername(), roles, true);

        return new AuthResponse(accessToken, refreshToken);
    }

    public Role addRole(Role role) {
        Role roles = roleRepository.findByRoleName(role.getRoleName())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Invalid role: " + role.getRoleName()
                        ));


        return roleRepository.save(roles);
    }
}


