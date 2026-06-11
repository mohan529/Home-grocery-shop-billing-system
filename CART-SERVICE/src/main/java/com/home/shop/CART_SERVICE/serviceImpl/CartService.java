package com.home.shop.CART_SERVICE.serviceImpl;

import com.home.shop.CART_SERVICE.entity.Cart;
import com.home.shop.CART_SERVICE.entity.CartItem;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;

    public CartService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String key(String userId) {
        return "cart:" + userId;
    }

    public Cart getCart(String userId) {
        Object obj = redisTemplate.opsForValue().get(key(userId));
        if (obj == null) {
            Cart cart = new Cart();
            cart.setUserId(userId);
            return cart;
        }
        return (Cart) obj;
    }

    public Cart addItem(String userId, CartItem item) {
        Cart cart = getCart(userId);

        cart.getItems().removeIf(i -> i.getProductId().equals(item.getProductId()));
        cart.getItems().add(item);

        redisTemplate.opsForValue().set(key(userId), cart);
        return cart;
    }

    public Cart removeItem(String userId, Long productId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        redisTemplate.opsForValue().set(key(userId), cart);
        return cart;
    }

    public void clearCart(String userId) {
        redisTemplate.delete(key(userId));
    }
}

