package com.home.shop.CART_SERVICE.controller;


import com.home.shop.CART_SERVICE.entity.Cart;
import com.home.shop.CART_SERVICE.entity.CartItem;
import com.home.shop.CART_SERVICE.serviceImpl.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return service.getCart(userId);
    }

    @PostMapping("/{userId}/add")
    public Cart addItem(@PathVariable String userId,
                        @RequestBody CartItem item) {
        return service.addItem(userId, item);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public Cart removeItem(@PathVariable String userId,
                           @PathVariable Long productId) {
        return service.removeItem(userId, productId);
    }

    @DeleteMapping("/{userId}/clear")
    public void clear(@PathVariable String userId) {
        service.clearCart(userId);
    }
}
