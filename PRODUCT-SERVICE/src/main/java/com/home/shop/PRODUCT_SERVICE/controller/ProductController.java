package com.home.shop.PRODUCT_SERVICE.controller;


import com.home.shop.PRODUCT_SERVICE.entity.Product;
import com.home.shop.PRODUCT_SERVICE.serviceImpl.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Product> all() {
        return service.getAll();
    }
}


