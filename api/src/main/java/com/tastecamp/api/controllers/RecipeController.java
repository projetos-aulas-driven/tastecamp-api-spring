package com.tastecamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tastecamp.api.dtos.RecipeDTO;
import com.tastecamp.api.models.RecipeModel;
import com.tastecamp.api.services.RecipeService;

import jakarta.validation.Valid;

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

    final RecipeService recipeService;

    RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping()
    public ResponseEntity<Object> getRecipes() {
        return ResponseEntity.status(HttpStatus.OK).body(recipeService.getRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRecipeById(@PathVariable("id") Long id) {
        Optional<RecipeModel> recipe = recipeService.getRecipeById(id);

        if (!recipe.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe with this id not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(recipe.get());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> createRecipe(@RequestBody @Valid RecipeDTO body) {
        Optional<RecipeModel> recipe = recipeService.createRecipe(body);

        if (!recipe.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A recipe with this title already exists");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(recipe.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecipe(@PathVariable("id") Long id, @RequestBody @Valid RecipeDTO body) {
        Optional<RecipeModel> recipe = recipeService.updateRecipe(id, body);

        if (!recipe.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(recipe.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRecipe(@PathVariable("id") Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}