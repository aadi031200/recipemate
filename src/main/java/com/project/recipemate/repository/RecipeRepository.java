package com.project.recipemate.repository;

import com.project.recipemate.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    Optional<Recipe> findByNameIgnoreCase(String name);

    Optional<Recipe> findBySpoonacularId(Long spoonacularId);
    Optional<Recipe> findBySearchTerm(String searchTerm);
}
