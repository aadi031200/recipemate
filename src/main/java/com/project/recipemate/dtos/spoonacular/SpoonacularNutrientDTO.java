package com.project.recipemate.dtos.spoonacular;

import lombok.Data;

@Data
public class SpoonacularNutrientDTO {

    private String name;
    private double amount;
    private String unit;
}
