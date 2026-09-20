package com.project.recipemate.dtos.groq;

import lombok.Data;

@Data
public class AiIngredientDTO {
    private String name;
    private double quantity;
    private String unit;
}
