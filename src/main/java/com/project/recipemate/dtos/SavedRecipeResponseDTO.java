package com.project.recipemate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavedRecipeResponseDTO {

    private long savedRecipeId;
    private Instant savedAt;
    private RecipeResponseDTO recipe;
}
