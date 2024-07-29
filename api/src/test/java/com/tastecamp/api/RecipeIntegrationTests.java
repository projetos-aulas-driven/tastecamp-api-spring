package com.tastecamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.tastecamp.api.dtos.RecipeDTO;
import com.tastecamp.api.models.CategoryModel;
import com.tastecamp.api.models.RecipeModel;
import com.tastecamp.api.models.UserModel;
import com.tastecamp.api.repositories.CategoryRepository;
import com.tastecamp.api.repositories.RecipeRepository;
import com.tastecamp.api.repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RecipeIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDatabase() {
        recipeRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    void givenRepeatedRecipe_whenCreatingRecipe_thenThrowsError() {
        // given
        UserModel user = new UserModel(null, "username", "email@email.com");
        UserModel createdUser = userRepository.save(user);

        CategoryModel category = new CategoryModel(null, "category 1");
        CategoryModel createdCategory = categoryRepository.save(category);

        RecipeModel recipeConflict = new RecipeModel(
                null,
                "Title",
                "Ingredients",
                "Steps",
                createdUser,
                List.of(createdCategory));
        recipeRepository.save(recipeConflict);

        RecipeDTO recipeDto = new RecipeDTO(
                "Title",
                "Ingredients",
                "Steps",
                createdUser.getId(),
                List.of(createdCategory.getId()));
        
        HttpEntity<RecipeDTO> body = new HttpEntity<>(recipeDto);

        // when
        ResponseEntity<String> response = restTemplate.exchange(
            "/recipes", // rota
            HttpMethod.POST, // método http
            body, // body da requisição
            String.class // classe que representa o tipo da sua resposa
        );

        // then
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("A recipe with this title already exists", response.getBody());
        assertEquals(1, recipeRepository.count());
    }

    @Test
    void givenWrongUserId_whenCreatingRecipe_thenThrowsError() {
        // given
        UserModel user = new UserModel(null, "username", "email@email.com");
        UserModel deletedUser = userRepository.save(user);
        userRepository.deleteById(deletedUser.getId());

        CategoryModel category = new CategoryModel(null, "category 1");
        CategoryModel createdCategory = categoryRepository.save(category);

        RecipeDTO recipeDto = new RecipeDTO(
                "Title",
                "Ingredients",
                "Steps",
                deletedUser.getId(),
                List.of(createdCategory.getId()));
        
        HttpEntity<RecipeDTO> body = new HttpEntity<>(recipeDto);

        // when
        ResponseEntity<String> response = restTemplate.exchange(
            "/recipes", // rota
            HttpMethod.POST, // método http
            body, // body da requisição
            String.class // classe que representa o tipo da sua resposa
        );

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Could not find user with this id", response.getBody());
        assertEquals(0, recipeRepository.count());
    }

    @Test
	void givenValidRecipe_whenCreatingRecipe_thenCreatesRecipe() {
        // given
        UserModel user = new UserModel(null, "username", "email@email.com");
        UserModel createdUser = userRepository.save(user);

        CategoryModel category = new CategoryModel(null, "category 1");
        CategoryModel createdCategory = categoryRepository.save(category);

        RecipeDTO recipeDto = new RecipeDTO(
                "Title",
                "Ingredients",
                "Steps",
                createdUser.getId(),
                List.of(createdCategory.getId()));
        
        HttpEntity<RecipeDTO> body = new HttpEntity<>(recipeDto);

        // when
        ResponseEntity<RecipeModel> response = restTemplate.exchange(
            "/recipes", // rota
            HttpMethod.POST, // método http
            body, // body da requisição
            RecipeModel.class // classe que representa o tipo da sua resposa
        );

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(recipeDto.getTitle(), response.getBody().getTitle());
        assertEquals(recipeDto.getAuthorId(), response.getBody().getAuthor().getId());
        assertEquals(1, recipeRepository.count());
    }
}
