// controller/UserPhotoController.java — new, separate controller
package com.project.recipemate.controller;

import com.project.recipemate.dtos.RecipePhotoResponseDTO;
import com.project.recipemate.service.RecipePhotoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/photos")
public class UserPhotoController {

    private final RecipePhotoService recipePhotoService;

    public UserPhotoController(RecipePhotoService recipePhotoService) {
        this.recipePhotoService = recipePhotoService;
    }

    @GetMapping
    public ResponseEntity<List<RecipePhotoResponseDTO>> getPhotos(@PathVariable Long userId) {
        return ResponseEntity.ok(recipePhotoService.getPhotosForUser(userId));
    }
}