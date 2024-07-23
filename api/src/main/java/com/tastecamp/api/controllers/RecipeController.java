package com.tastecamp.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    
   @GetMapping()
    public String getRecipes() {
        return "Hello World";
    }   
}
