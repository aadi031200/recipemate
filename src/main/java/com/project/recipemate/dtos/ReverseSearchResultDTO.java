package com.project.recipemate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReverseSearchResultDTO {
    private Long spoonacularId;
    private String title;
    private String image;
    private int usedIngredientCount;
    private int missedIngredientCount;
    private List<String> missingIngredients;
}
