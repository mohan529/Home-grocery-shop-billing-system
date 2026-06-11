package com.home.shop.USER_SERVICE.repository;


import com.home.shop.USER_SERVICE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}