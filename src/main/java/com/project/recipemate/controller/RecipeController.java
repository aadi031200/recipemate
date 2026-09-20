package com.project.recipemate.controller;

import com.project.recipemate.dtos.IngredientSearchRequestDTO;
import com.project.recipemate.dtos.ReverseSearchResultDTO;
import com.project.recipemate.dtos.ScaledIngredientDTO;
import com.project.recipemate.entities.Recipe;
import com.project.recipemate.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/search")
    public ResponseEntity<Recipe> searchRecipe(@RequestParam String name){
        Recipe recipe=recipeService.getRecipeByName(name);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}/scale")
    public ResponseEntity<List<ScaledIngredientDTO>> scaleRecipe(@PathVariable Long id, @RequestParam int  servings){
        return ResponseEntity.ok(recipeService.getScaledIngredients(id,servings));
    }
    @PostMapping("/search-by-ingredients")
    public ResponseEntity<List<ReverseSearchResultDTO>> searchByIngredients(@RequestBody IngredientSearchRequestDTO request){
        return ResponseEntity.ok(recipeService.findRecipesByIngredients(request.getIngredients()));
    }
}
