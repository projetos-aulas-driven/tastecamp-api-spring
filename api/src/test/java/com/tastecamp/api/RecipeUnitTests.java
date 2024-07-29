package com.tastecamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.tastecamp.api.dtos.RecipeDTO;
import com.tastecamp.api.exceptions.RecipeTitleConflictException;
import com.tastecamp.api.exceptions.UserNotFoundException;
import com.tastecamp.api.models.CategoryModel;
import com.tastecamp.api.models.RecipeModel;
import com.tastecamp.api.models.UserModel;
import com.tastecamp.api.repositories.CategoryRepository;
import com.tastecamp.api.repositories.RecipeRepository;
import com.tastecamp.api.repositories.UserRepository;
import com.tastecamp.api.services.RecipeService;

@SpringBootTest
class RecipeUnitTests {

	@InjectMocks
	private RecipeService recipeService;

	@Mock
	private RecipeRepository recipeRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private CategoryRepository categoryRepository;

	@Test
	void givenRepeatedRecipe_whenCreatingRecipe_thenThrowsError() {
		// given => setup das condições de teste
		Long authorId = 1L;
		List<Long> categoryIds = List.of(1L, 2L);
		RecipeDTO recipe = new RecipeDTO("Test", "Test", "Test", authorId, categoryIds);

		doReturn(true).when(recipeRepository).existsByTitle(any());

		// when => operação que vai acontecer
		RecipeTitleConflictException exception = assertThrows(
				RecipeTitleConflictException.class,
				() -> recipeService.createRecipe(recipe));

		// then => verificações do teste
		verify(recipeRepository, times(1)).existsByTitle(any());
		verify(userRepository, times(0)).findById(any());
		verify(recipeRepository, times(0)).save(any());
		assertNotNull(exception);
		assertEquals("A recipe with this title already exists", exception.getMessage());
	}

	@Test
	void givenWrongUserId_whenCreatingRecipe_thenThrowsError() {
		// given => setup das condições de teste
		RecipeDTO recipe = new RecipeDTO("Test", "Test", "Test", 1L, List.of(1L, 2L));

		doReturn(false).when(recipeRepository).existsByTitle(any());
		doReturn(Optional.empty()).when(userRepository).findById(any());

		// when => operação que vai acontecer
		UserNotFoundException exception = assertThrows(
				UserNotFoundException.class,
				() -> recipeService.createRecipe(recipe));

		// then => verificações do teste
		verify(recipeRepository, times(1)).existsByTitle(any());
		verify(userRepository, times(1)).findById(any());
		verify(recipeRepository, times(0)).save(any());
		assertNotNull(exception);
		assertEquals("Could not find user with this id", exception.getMessage());
	}

	@Test
	void givenValidRecipe_whenCreatingRecipe_thenCreatesRecipe() {
		// given => setup das condições de teste
		RecipeDTO recipe = new RecipeDTO("Test", "Test", "Test", 1L, List.of(1L, 2L));
		UserModel user = new UserModel(1L, "Test", "test@test.com");
		List<CategoryModel> categories = List.of(new CategoryModel(1L, "Test"));
		RecipeModel newRecipe = new RecipeModel(recipe, user, categories);

		doReturn(false).when(recipeRepository).existsByTitle(any());
		doReturn(Optional.of(user)).when(userRepository).findById(any());
		doReturn(categories).when(categoryRepository).findAllById(any());
		doReturn(newRecipe).when(recipeRepository).save(any());

		// when => operação que vai acontecer
		RecipeModel result = recipeService.createRecipe(recipe);

		// then => verificações do teste
		verify(recipeRepository, times(1)).existsByTitle(any());
		verify(userRepository, times(1)).findById(any());
		verify(categoryRepository, times(1)).findAllById(any());
		verify(recipeRepository, times(1)).save(any());
		assertEquals(newRecipe, result);
	}
}
