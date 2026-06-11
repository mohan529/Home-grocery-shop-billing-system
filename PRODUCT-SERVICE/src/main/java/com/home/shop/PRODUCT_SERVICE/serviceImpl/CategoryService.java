package com.home.shop.PRODUCT_SERVICE.serviceImpl;


import com.home.shop.PRODUCT_SERVICE.entity.Category;
import com.home.shop.PRODUCT_SERVICE.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category create(Category category) {
        return repository.save(category);
    }

    public List<Category> getAll() {
        return repository.findAll();
    }
}


