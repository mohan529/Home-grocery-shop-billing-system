package com.home.shop.PRODUCT_SERVICE.repository;

import com.home.shop.PRODUCT_SERVICE.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
