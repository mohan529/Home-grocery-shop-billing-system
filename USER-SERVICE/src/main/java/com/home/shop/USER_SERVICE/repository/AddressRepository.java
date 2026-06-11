package com.home.shop.USER_SERVICE.repository;

import com.home.shop.USER_SERVICE.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
