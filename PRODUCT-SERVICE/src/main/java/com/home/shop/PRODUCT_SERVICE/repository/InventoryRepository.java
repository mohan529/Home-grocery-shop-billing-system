package com.home.shop.PRODUCT_SERVICE.repository;


import com.home.shop.PRODUCT_SERVICE.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
