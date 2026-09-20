package com.project.recipemate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScaledIngredientDTO {

    private String name;
    private double scaledQuantity;
    private String  unit;
}
