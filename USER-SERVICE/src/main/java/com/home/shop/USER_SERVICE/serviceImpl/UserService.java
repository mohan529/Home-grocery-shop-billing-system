package com.home.shop.USER_SERVICE.serviceImpl;


import com.home.shop.USER_SERVICE.entity.User;
import com.home.shop.USER_SERVICE.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) {
        return repository.save(user);
    }

    public User getUser(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}


