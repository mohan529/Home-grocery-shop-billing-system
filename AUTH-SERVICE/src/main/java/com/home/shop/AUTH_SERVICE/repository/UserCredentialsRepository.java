package com.home.shop.AUTH_SERVICE.repository;

import com.home.shop.AUTH_SERVICE.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByUsername(String username);

    boolean existsByUsername(String username);

}
