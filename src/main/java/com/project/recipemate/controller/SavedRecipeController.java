package com.project.recipemate.controller;

import com.project.recipemate.dtos.SavedRecipeResponseDTO;
import com.project.recipemate.repository.SavedRecipeRepository;
import com.project.recipemate.service.SavedRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/saved-recipes")
public class SavedRecipeController {
    private final SavedRecipeService savedRecipeService;

    public SavedRecipeController(SavedRecipeService savedRecipeService) {
        this.savedRecipeService = savedRecipeService;
    }

    @PostMapping("/{recipeId}")
    public ResponseEntity<SavedRecipeResponseDTO> saveRecipe(
            @PathVariable Long userId, @PathVariable Long recipeId)
    {
        return ResponseEntity.ok(savedRecipeService.saveRecipe(userId, recipeId));
    }

    @GetMapping
    public ResponseEntity<List<SavedRecipeResponseDTO>> getSavedRecipes(@PathVariable Long userId){
        return ResponseEntity.ok(savedRecipeService.getSavedRecipes(userId));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> unsaveRecipe(@PathVariable Long userId, @PathVariable Long recipeId){
        savedRecipeService.unsavedRecipe(userId, recipeId);

        return ResponseEntity.noContent().build();
    }
}
