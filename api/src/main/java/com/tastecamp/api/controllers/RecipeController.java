package com.tastecamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/recipes")
public class RecipeController {
    
   @GetMapping()
    public String getRecipes() {
        return "Lista de Receitas";
    }

    @GetMapping("/{id}")
    public String getRecipeById(@PathVariable("id") Long id) {
        return "Receita de id " + id;
    }

    @PostMapping()
    public String createRecipe(@RequestBody String body) {
        return body;
    }

    @PutMapping("/{id}")
    public String updateRecipe(@PathVariable("id") Long id, @RequestBody String body) {
        return "Edição da receita " + id + body;
    }

    @DeleteMapping("/{id}")
    public String deleteRecipe(@PathVariable("id") Long id) {
        return "Deletando item " + id;
    }
    
}
