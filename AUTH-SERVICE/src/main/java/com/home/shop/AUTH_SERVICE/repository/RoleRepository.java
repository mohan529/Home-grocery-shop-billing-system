package com.home.shop.AUTH_SERVICE.repository;

import com.home.shop.AUTH_SERVICE.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(Role.RoleName name);
}
