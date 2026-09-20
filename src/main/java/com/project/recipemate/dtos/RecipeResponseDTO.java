package com.project.recipemate.dtos;

import com.project.recipemate.entities.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDTO {
    private Long recipeId;
    private String name;
    private int servings;
    private String instructions;
    private List<IngredientResponseDTO> ingredients;
    private NutritionResponseDTO nutrition;
}
