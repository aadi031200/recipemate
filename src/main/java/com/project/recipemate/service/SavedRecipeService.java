package com.project.recipemate.service;

import com.project.recipemate.dtos.IngredientResponseDTO;
import com.project.recipemate.dtos.NutritionResponseDTO;
import com.project.recipemate.dtos.RecipeResponseDTO;
import com.project.recipemate.dtos.SavedRecipeResponseDTO;
import com.project.recipemate.entities.Recipe;
import com.project.recipemate.entities.SavedRecipe;
import com.project.recipemate.entities.User;
import com.project.recipemate.repository.RecipeRepository;
import com.project.recipemate.repository.SavedRecipeRepository;
import com.project.recipemate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedRecipeService {

    private final SavedRecipeRepository savedRecipeRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public SavedRecipeService(SavedRecipeRepository savedRecipeRepository,
                              UserRepository userRepository,
                              RecipeRepository recipeRepository) {
        this.savedRecipeRepository = savedRecipeRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public SavedRecipeResponseDTO saveRecipe(Long userId, Long recipeId) {
        if(savedRecipeRepository.existsByUserUserIdAndRecipeRecipeId(userId, recipeId)) {
            throw new IllegalArgumentException("Recipe already saved by this user");
        }

        User user=userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"+userId));

        Recipe recipe=recipeRepository.findById(recipeId)
                .orElseThrow(()->new RuntimeException("Recipe not found"+recipeId));

        SavedRecipe savedRecipe=new SavedRecipe();
        savedRecipe.setUser(user);
        savedRecipe.setRecipe(recipe);
        savedRecipe.setSavedAt(Instant.now());

        savedRecipe =savedRecipeRepository.save(savedRecipe);
        return mapToDTO(savedRecipe);
    }

    public List<SavedRecipeResponseDTO> getSavedRecipes(Long userId) {
        return savedRecipeRepository.findByUserUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void unsavedRecipe(Long userId, Long recipeId) {
        SavedRecipe savedRecipe=savedRecipeRepository.findByUserUserIdAndRecipeRecipeId(userId,recipeId)
                .orElseThrow(()->new RuntimeException("Saved Recipe not found"));
        savedRecipeRepository.delete(savedRecipe);
    }

    private SavedRecipeResponseDTO mapToDTO(SavedRecipe savedRecipe) {

        Recipe recipe=savedRecipe.getRecipe();

        List<IngredientResponseDTO> ingredientDTOs=recipe.getIngredients().stream()
                .map(ingredient -> new IngredientResponseDTO(
                        ingredient.getName(),
                        ingredient.getQuantity(),
                        ingredient.getUnit()
                ))
                .collect(Collectors.toList());

        NutritionResponseDTO nutritionDTO=new NutritionResponseDTO(
                recipe.getNutrition().getCalories(),
                recipe.getNutrition().getProtein(),
                recipe.getNutrition().getFat(),
                recipe.getNutrition().getCarbs(),
                recipe.getNutrition().getSugar()
        );

        RecipeResponseDTO recipeDTO=new RecipeResponseDTO(
                recipe.getRecipeId(),
                recipe.getName(),
                recipe.getServings(),
                recipe.getInstructions(),
                ingredientDTOs,
                nutritionDTO
        );

        SavedRecipeResponseDTO dto = new SavedRecipeResponseDTO();
        dto.setSavedRecipeId(savedRecipe.getId());
        dto.setSavedAt(savedRecipe.getSavedAt());
        dto.setRecipe(recipeDTO);
        return dto;
    }
}
