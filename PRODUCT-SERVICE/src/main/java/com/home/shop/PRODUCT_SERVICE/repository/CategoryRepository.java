package com.home.shop.PRODUCT_SERVICE.repository;

import com.home.shop.PRODUCT_SERVICE.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
