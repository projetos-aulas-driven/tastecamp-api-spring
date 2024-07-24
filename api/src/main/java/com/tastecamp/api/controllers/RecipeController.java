package com.tastecamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tastecamp.api.dtos.RecipeDTO;
import com.tastecamp.api.models.RecipeModel;
import com.tastecamp.api.repositories.RecipeRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    final RecipeRepository recipeRepository;

    RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping()
    public ResponseEntity<Object> getRecipes() {
        return ResponseEntity.status(HttpStatus.OK).body(recipeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRecipeById(@PathVariable("id") Long id) {
        Optional<RecipeModel> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe with this id not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(recipe.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createRecipe(@RequestBody @Valid RecipeDTO body) {
        RecipeModel recipe = new RecipeModel(body);
        recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable("id") Long id, @RequestBody @Valid RecipeDTO body) {
        Optional<RecipeModel> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        RecipeModel newRecipe = new RecipeModel(body);
        newRecipe.setId(id);
        recipeRepository.save(newRecipe);
        return ResponseEntity.status(HttpStatus.OK).body(newRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable("id") Long id) {
        recipeRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}