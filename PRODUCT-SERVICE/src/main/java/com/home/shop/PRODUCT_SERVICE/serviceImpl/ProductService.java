package com.home.shop.PRODUCT_SERVICE.serviceImpl;


import com.home.shop.PRODUCT_SERVICE.entity.Product;
import com.home.shop.PRODUCT_SERVICE.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public Product getById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Product> getAll() {
        return repository.findAll();
    }
}

