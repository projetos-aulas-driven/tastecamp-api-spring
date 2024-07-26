package com.tastecamp.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tastecamp.api.models.RecipeModel;


@Repository
public interface RecipeRepository extends JpaRepository<RecipeModel, Long> {
    boolean existsByTitle(String title);

    @Query(
        nativeQuery = true,
        value = "SELECT r.id, title, ingredients, steps, author_id FROM \"tb-recipes\" r " + 
            "JOIN \"recipe-category\" rc ON rc.recipe_id = r.id " +
            "JOIN \"tb-categories\" c ON rc.category_id = c.id " +
            "WHERE c.id = :categoryId;"
    )
    List<RecipeModel> findByCategoryId(@Param("categoryId") Long categoryId);
}


