package com.tastecamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tastecamp.api.dtos.RecipeDTO;
import com.tastecamp.api.exceptions.RecipeTitleConflictException;
import com.tastecamp.api.exceptions.UserNotFoundException;
import com.tastecamp.api.models.CategoryModel;
import com.tastecamp.api.models.RecipeModel;
import com.tastecamp.api.models.UserModel;
import com.tastecamp.api.repositories.CategoryRepository;
import com.tastecamp.api.repositories.RecipeRepository;
import com.tastecamp.api.repositories.UserRepository;

@Service
public class RecipeService {

    final RecipeRepository recipeRepository;
    final UserRepository userRepository;
    final CategoryRepository categoryRepository;

    RecipeService(RecipeRepository recipeRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<RecipeModel> getRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<RecipeModel> getRecipeById(Long id) {
        Optional<RecipeModel> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()) {
            return Optional.empty();
        } else {
            return recipe;
        }
    }

    public RecipeModel createRecipe(RecipeDTO body) {
        if (recipeRepository.existsByTitle(body.getTitle())) {
            throw new RecipeTitleConflictException("A recipe with this title already exists");
        }

        UserModel user = userRepository
            .findById(body.getAuthorId())
            .orElseThrow(() -> new UserNotFoundException("Could not find user with this id"));

        List<CategoryModel> categories = categoryRepository.findAllById(body.getCategoryIds());
        
        RecipeModel recipe = new RecipeModel(body, user, categories);
        return recipeRepository.save(recipe);
    }

    public Optional<RecipeModel> updateRecipe(Long id, RecipeDTO body) {
        Optional<RecipeModel> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()) {
            return Optional.empty();
        }

        RecipeModel newRecipe = new RecipeModel(body);
        newRecipe.setId(id);
        recipeRepository.save(newRecipe);
        return Optional.of(newRecipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public List<RecipeModel> getRecipesByCategoryId(Long id) {
        return recipeRepository.findByCategoryId(id);
    }
}
