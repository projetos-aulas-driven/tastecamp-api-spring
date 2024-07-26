package com.tastecamp.api.services;

import org.springframework.stereotype.Service;

import com.tastecamp.api.dtos.CategoryDTO;
import com.tastecamp.api.models.CategoryModel;
import com.tastecamp.api.repositories.CategoryRepository;

@Service
public class CategoryService {

    final CategoryRepository categoryRepository;

    CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public CategoryModel createCategory(CategoryDTO body) {
        CategoryModel category = new CategoryModel(body);
        return categoryRepository.save(category);
    }
    
}
