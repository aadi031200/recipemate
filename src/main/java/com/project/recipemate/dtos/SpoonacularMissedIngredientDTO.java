package com.project.recipemate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpoonacularMissedIngredientDTO {
    private String name;
    private String original;
}
