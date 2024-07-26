package com.tastecamp.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tastecamp.api.dtos.RecipeDTO;
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

    public Optional<RecipeModel> createRecipe(RecipeDTO body) {
        if (recipeRepository.existsByTitle(body.getTitle())) {
            return Optional.empty();
        }

        Optional<UserModel> user = userRepository.findById(body.getAuthorId());

        if(!user.isPresent()) {
            return Optional.empty();
        }

        List<CategoryModel> categories = categoryRepository.findAllById(body.getCategoryIds());
        
        RecipeModel recipe = new RecipeModel(body, user.get(), categories);
        recipeRepository.save(recipe);
        return Optional.of(recipe);
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
