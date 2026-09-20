package com.project.recipemate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstitutionResponseDTO {
    private String ingredient;
    private String substitute;
    private String notes;
    private String source;
}

