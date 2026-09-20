package com.project.recipemate.repository;

import com.project.recipemate.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient,Long> {

    List<Ingredient> findByRecipeRecipeId(Long recipeId);
}
