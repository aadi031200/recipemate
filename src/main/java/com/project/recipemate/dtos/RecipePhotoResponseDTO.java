package com.project.recipemate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipePhotoResponseDTO {
    private Long Photo_id;
    private String imageUrl;
    private String caption;
    private Instant takenAt;

}
