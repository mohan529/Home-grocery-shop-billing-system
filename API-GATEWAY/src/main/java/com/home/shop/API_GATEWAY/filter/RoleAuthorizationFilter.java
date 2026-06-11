package com.home.shop.API_GATEWAY.filter;


import com.home.shop.API_GATEWAY.config.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class RoleAuthorizationFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    public RoleAuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().toString();

        if (path.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange);
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validate(token)) {
            return unauthorized(exchange);
        }

        List<String> roles = jwtUtil.getRoles(token);

        if (!isAuthorized(path, roles)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean isAuthorized(String path, List<String> roles) {

        if (path.startsWith("/api/users")) {
            return roles.contains("ADMIN");
        }

        if (path.startsWith("/api/products")) {
            return roles.contains("ADMIN") || roles.contains("CUSTOMER");
        }

        if (path.startsWith("/api/orders")) {
            return roles.contains("CUSTOMER") || roles.contains("DELIVERY");
        }

        return true;
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

