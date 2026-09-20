package com.project.recipemate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionResponseDTO {
    private double calories;
    private double protein;
    private double fats;
    private double carbs;
    private double sugar;
}
