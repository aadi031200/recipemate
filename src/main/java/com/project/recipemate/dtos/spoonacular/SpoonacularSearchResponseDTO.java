package com.project.recipemate.dtos.spoonacular;

import lombok.Data;

import java.util.List;
@Data
public class SpoonacularSearchResponseDTO {

    private List<SpoonacularSearchResultDTO> results;
    private int totalResults;
}
