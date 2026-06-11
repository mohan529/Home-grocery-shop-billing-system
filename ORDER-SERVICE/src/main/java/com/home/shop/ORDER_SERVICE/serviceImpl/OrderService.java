package com.home.shop.ORDER_SERVICE.serviceImpl;


import com.home.shop.CART_SERVICE.entity.Cart;
import com.home.shop.ORDER_SERVICE.entity.Order;
import com.home.shop.ORDER_SERVICE.entity.OrderItem;
import com.home.shop.ORDER_SERVICE.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Order placeOrder(String userId) {
        // 1️⃣ Get Cart from Cart Service
        Cart cart = restTemplate.getForObject(
                "http://CART-SERVICE/api/cart/" + userId, Cart.class);

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2️⃣ Convert CartItem → OrderItem
        List<OrderItem> items = cart.getItems().stream().map(ci -> {
            OrderItem oi = new OrderItem();
            oi.setProductId(ci.getProductId());
            oi.setProductName(ci.getProductName());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(ci.getPrice());
            return oi;
        }).collect(Collectors.toList());

        // 3️⃣ Calculate total
        double total = items.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        // 4️⃣ Create Order
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(items);
        order.setTotalPrice(total);
        order.setOrderTime(LocalDateTime.now());

        repository.save(order);

        // 5️⃣ Clear Cart
        restTemplate.delete("http://CART-SERVICE/api/cart/" + userId + "/clear");

        return order;
    }

    public List<Order> getOrders(String userId, boolean isAdmin) {
        if (isAdmin) {
            return repository.findAll();
        } else {
            return repository.findByUserId(userId);
        }
    }
}

