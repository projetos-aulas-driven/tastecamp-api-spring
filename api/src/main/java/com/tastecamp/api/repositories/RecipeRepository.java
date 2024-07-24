package com.tastecamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tastecamp.api.models.RecipeModel;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeModel, Long> {
    
}
