package com.home.shop.ORDER_SERVICE.controller;


import com.home.shop.ORDER_SERVICE.config.JwtUtil;
import com.home.shop.ORDER_SERVICE.entity.Order;
import com.home.shop.ORDER_SERVICE.serviceImpl.OrderService;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/place")
    public Order placeOrder(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = jwtUtil.getClaims(token);
        String userId = claims.getSubject();
        return service.placeOrder(userId);
    }

    @GetMapping
    public List<Order> getOrders(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Claims claims = jwtUtil.getClaims(token);
        String userId = claims.getSubject();
        String role = claims.get("role", String.class);
        boolean isAdmin = "ADMIN".equals(role);

        return service.getOrders(userId, isAdmin);
    }
}

