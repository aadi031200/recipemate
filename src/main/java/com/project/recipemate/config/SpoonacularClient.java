package com.project.recipemate.config;

import com.project.recipemate.dtos.SpoonacularIngredientMatchDTO;
import com.project.recipemate.dtos.spoonacular.SpoonacularNutritionDTO;
import com.project.recipemate.dtos.spoonacular.SpoonacularRecipeInfoDTO;
import com.project.recipemate.dtos.spoonacular.SpoonacularSearchResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class SpoonacularClient {

    private final RestClient restClient;

    @Value("${spoonacular.api.key}")
    private String apiKey;
    @Value("${spoonacular.api.base-url}")
    private String baseUrl;

    public SpoonacularClient(RestClient restClient) {
        this.restClient = restClient;
    }

    // 1. Search Recipe by dish name
    public SpoonacularSearchResponseDTO searchRecipes(String query){
        String url=baseUrl+"/recipes/complexSearch?query="+query+"&number=1&apiKey="+apiKey;

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(SpoonacularSearchResponseDTO.class);
    }

    // 2.Get full recipe info (ingredient) by recipeId
    public SpoonacularRecipeInfoDTO getRecipeInfo(Long recipeId){
        String url=baseUrl+"/recipes/"+recipeId+"/information?includeNutrition=true&apiKey="+apiKey;

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(SpoonacularRecipeInfoDTO.class);
    }

//    // 3.Get nutrition  info by recipe id
//    public SpoonacularNutritionDTO getNutrition(Long recipeId){
//        String url=baseUrl+"/recipes/"+recipeId+"/nutritionWidget.json?apiKey="+apiKey;
//
//        return restClient.get()
//                .uri(url)
//                .retrieve()
//                .body(SpoonacularNutritionDTO.class);
//    }


    public List<SpoonacularIngredientMatchDTO> findByIngredients(List<String> ingredients){
        String ingredientList=String.join(",",ingredients);

        SpoonacularIngredientMatchDTO[] response=restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.spoonacular.com")
                        .path("/recipes/findByIngredients")
                        .queryParam("ingredients",ingredientList)
                        .queryParam("number",5)
                        .queryParam("ranking",1)
                        .queryParam("apiKey",apiKey)
                        .build())
                .retrieve()
                .body(SpoonacularIngredientMatchDTO[].class);

        return response!=null? Arrays.asList(response):List.of();
    }
}
