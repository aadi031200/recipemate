package com.project.recipemate.controller;

import com.project.recipemate.dtos.RecipePhotoResponseDTO;
import com.project.recipemate.service.RecipePhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/recipes/{recipeId}/photos")
public class RecipePhotoController {

    private final RecipePhotoService recipePhotoService;

    public RecipePhotoController(RecipePhotoService recipePhotoService) {
        this.recipePhotoService = recipePhotoService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipePhotoResponseDTO> uploadPhoto(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "caption",required = false) String caption) throws IOException {
        return  ResponseEntity.ok(recipePhotoService.uploadPhoto(userId,recipeId,file,caption));
    }

}
