package com.project.recipemate.dtos.spoonacular;

import lombok.Data;

import java.util.List;
@Data
public class SpoonacularNutritionDTO {

    private List<SpoonacularNutrientDTO> nutrients;
}
