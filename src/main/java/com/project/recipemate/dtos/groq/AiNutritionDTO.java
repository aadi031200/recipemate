package com.project.recipemate.dtos.groq;

import lombok.Data;

@Data
public class AiNutritionDTO {
    private double calories;
    private double protein;
    private double fat;
    private double carbs;
    private double sugar;
}
