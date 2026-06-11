package com.home.shop.USER_SERVICE.controller;


import com.home.shop.USER_SERVICE.entity.User;
import com.home.shop.USER_SERVICE.serviceImpl.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return service.getUser(id);
    }

    @GetMapping
    public List<User> all() {
        return service.getAllUsers();
    }
}
