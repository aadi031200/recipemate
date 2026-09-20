package com.project.recipemate.repository;

import com.project.recipemate.entities.RecipePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipePhotoRepository extends JpaRepository<RecipePhoto,Long> {
    List<RecipePhoto> findByUserUserId(Long userId);
}
