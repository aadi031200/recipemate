package com.project.recipemate.dtos.spoonacular;

import lombok.Data;

import java.util.List;
@Data
public class SpoonacularRecipeInfoDTO {

    private Long id;
    private String title;
    private int servings;
    private String instructions;
    private List<SpoonacularIngredientDTO> extendedIngredients;
    private SpoonacularNutritionDTO nutrition;
}
