package com.project.recipemate.service;

import com.project.recipemate.config.GroqClient;
import com.project.recipemate.config.SpoonacularClient;
import com.project.recipemate.dtos.ReverseSearchResultDTO;
import com.project.recipemate.dtos.ScaledIngredientDTO;
import com.project.recipemate.dtos.SpoonacularIngredientMatchDTO;
import com.project.recipemate.dtos.SpoonacularMissedIngredientDTO;
import com.project.recipemate.dtos.groq.AiRecipeResponseDTO;
import com.project.recipemate.dtos.spoonacular.SpoonacularNutrientDTO;
import com.project.recipemate.dtos.spoonacular.SpoonacularNutritionDTO;
import com.project.recipemate.dtos.spoonacular.SpoonacularRecipeInfoDTO;
import com.project.recipemate.dtos.spoonacular.SpoonacularSearchResponseDTO;
import com.project.recipemate.entities.Ingredient;
import com.project.recipemate.entities.Nutrition;
import com.project.recipemate.entities.Recipe;
import com.project.recipemate.exceptions.RecipeNotFoundException;
import com.project.recipemate.repository.IngredientRepository;
import com.project.recipemate.repository.NutritionRepository;
import com.project.recipemate.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final NutritionRepository nutritionRepository;
    private final SpoonacularClient spoonacularClient;
    private final GroqClient groqClient;

    public RecipeService(RecipeRepository recipeRepository,
                         IngredientRepository ingredientRepository,
                         NutritionRepository nutritionRepository,
                         SpoonacularClient spoonacularClient,
                         GroqClient groqClient) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.nutritionRepository = nutritionRepository;
        this.spoonacularClient = spoonacularClient;
        this.groqClient = groqClient;
    }

    public Recipe getRecipeByName(String dishName){
        String normalizedTerm = dishName.trim().toLowerCase();
        // Step 1: Check DB FIRST using the normalized search term (no API call yet)
        Optional<Recipe> cached = recipeRepository.findBySearchTerm(normalizedTerm);
        if (cached.isPresent()) {
            return cached.get();
        }

        // Step 1: Search Spoonacular first to get the canonical recipe ID

        SpoonacularSearchResponseDTO searchResponse=spoonacularClient.searchRecipes(dishName);
//            System.out.println("DEBUG searchResponse: " + searchResponse);
//            if (searchResponse != null) {
//                System.out.println("DEBUG results: " + searchResponse.getResults());
//            }
            if(searchResponse.getResults()==null || searchResponse.getResults().isEmpty()){
                return generateAndSaveAiRecipe(dishName, normalizedTerm);
            }

            Long spoonacularId =searchResponse.getResults().get(0).getId();

        //Step 2: Check Db first using the Spoonacular ID (not the user's search text)
        Optional<Recipe> existing= recipeRepository.findBySpoonacularId(spoonacularId);

        if(existing.isPresent()){
            return existing.get();
        }

            //Step 3:fetch full details (ingredients + Nutrients)

            SpoonacularRecipeInfoDTO info =spoonacularClient.getRecipeInfo(spoonacularId);

            //Step 4: Convert dto -> entities and save

            Recipe recipe= mapToRecipeEntity(info);
            recipe.setSpoonacularId(spoonacularId);
            recipe.setSearchTerm(normalizedTerm);
            recipe =recipeRepository.save(recipe);

            List<Ingredient> ingredients=mapToIngredientEntities(info,recipe);
            ingredientRepository.saveAll(ingredients);

            Nutrition nutrition=mapToNutritionEntity(info,recipe);
            nutritionRepository.save(nutrition);

            recipe.setIngredients(ingredients);
            recipe.setNutrition(nutrition);
            return recipe;

    }


    private Recipe mapToRecipeEntity(SpoonacularRecipeInfoDTO info) {
        Recipe recipe = new Recipe();
        recipe.setName(info.getTitle());
        recipe.setServings(info.getServings());
        recipe.setInstructions(info.getInstructions());
        recipe.setSource("SPOONACULAR");
        return recipe;
    }

    private List<Ingredient> mapToIngredientEntities(SpoonacularRecipeInfoDTO info, Recipe recipe) {

        return info.getExtendedIngredients().stream()
                .map(dto->{
                    Ingredient ing = new Ingredient();
                    ing.setName(dto.getName());
                    ing.setQuantity(dto.getAmount());
                    ing.setUnit(dto.getUnit());
                    ing.setRecipe(recipe);
                    return ing;
                })
                .collect(Collectors.toList());
    }

    private Nutrition mapToNutritionEntity(SpoonacularRecipeInfoDTO info, Recipe recipe) {

        Nutrition nutrition=new Nutrition();
        nutrition.setRecipe(recipe);

        for(SpoonacularNutrientDTO nutrient:info.getNutrition().getNutrients()){
            switch (nutrient.getName()){
                case "Calories"-> nutrition.setCalories(nutrient.getAmount());
                case "Protein"-> nutrition.setProtein(nutrient.getAmount());
                case "Fat" -> nutrition.setFat(nutrient.getAmount());
                case "Carbohydrates"-> nutrition.setCarbs(nutrient.getAmount());
                case "Sugar"-> nutrition.setSugar(nutrient.getAmount());
            }
        }
        return nutrition;
    }

    public List<ScaledIngredientDTO> getScaledIngredients(Long recipeId,int requestServings){

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(()->new RecipeNotFoundException("Recipe not found with id: "+recipeId));

        double scaleFactor =(double)requestServings/recipe.getServings();

        return recipe.getIngredients().stream()
                .map(ingredient -> new ScaledIngredientDTO(
                        ingredient.getName(),
                        ingredient.getQuantity()*scaleFactor,
                        ingredient.getUnit()
                ))
                .collect(Collectors.toList());

     }

    // In RecipeService.java
    public List<ReverseSearchResultDTO> findRecipesByIngredients(List<String> ingredients) {
        List<SpoonacularIngredientMatchDTO> matches = spoonacularClient.findByIngredients(ingredients);

        return matches.stream()
                .map(match -> {
                    List<String> missing = match.getMissedIngredients().stream()
                            .map(SpoonacularMissedIngredientDTO::getOriginal)
                            .collect(Collectors.toList());

                    return new ReverseSearchResultDTO(
                            match.getId(),
                            match.getTitle(),
                            match.getImage(),
                            match.getUsedIngredientCount(),
                            match.getMissedIngredientCount(),
                            missing
                    );
                })
                .collect(Collectors.toList());
    }

    private Recipe generateAndSaveAiRecipe(String dishName,String normalizedTerm){
        AiRecipeResponseDTO aiRecipe=groqClient.generateRecipe(dishName);

        Recipe recipe = new Recipe();
        recipe.setName(aiRecipe.getName());
        recipe.setServings(aiRecipe.getServings());
        recipe.setInstructions(aiRecipe.getInstructions());
        recipe.setSource("AI");
        recipe.setSearchTerm(normalizedTerm);

        Recipe savedRecipe = recipeRepository.save(recipe);

        List<Ingredient> ingredients=aiRecipe.getIngredients().stream()
                .map(dto -> {
                    Ingredient ing=new Ingredient();
                    ing.setName(dto.getName());
                    ing.setQuantity(dto.getQuantity());
                    ing.setUnit(dto.getUnit());
                    ing.setRecipe(savedRecipe);
                    return ing;
        })
                .collect(Collectors.toList());
        ingredientRepository.saveAll(ingredients);


        Nutrition nutrition = new Nutrition();
        nutrition.setRecipe(recipe);
        nutrition.setCalories(aiRecipe.getNutrition().getCalories());
        nutrition.setProtein(aiRecipe.getNutrition().getProtein());
        nutrition.setFat(aiRecipe.getNutrition().getFat());
        nutrition.setCarbs(aiRecipe.getNutrition().getCarbs());
        nutrition.setSugar(aiRecipe.getNutrition().getSugar());
        nutritionRepository.save(nutrition);

        recipe.setIngredients(ingredients);
        recipe.setNutrition(nutrition);
        return recipe;
    }
}
