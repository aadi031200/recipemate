package com.project.recipemate.repository;

import com.project.recipemate.entities.SavedRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedRecipeRepository extends JpaRepository<SavedRecipe,Long> {

    List<SavedRecipe> findByUserUserId(Long userId);
    boolean existsByUserUserIdAndRecipeRecipeId(Long userId,Long recipeId);
    Optional<SavedRecipe> findByUserUserIdAndRecipeRecipeId(Long userId, Long recipeId);

}
