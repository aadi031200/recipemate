package com.project.recipemate.dtos.groq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRecipeResponseDTO {
    private String name;
    private int servings;
    private String instructions;
    private List<AiIngredientDTO> ingredients;
    private AiNutritionDTO  nutrition;
}
