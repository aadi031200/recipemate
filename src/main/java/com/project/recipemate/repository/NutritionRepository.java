package com.project.recipemate.repository;

import com.project.recipemate.entities.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NutritionRepository extends JpaRepository<Nutrition,Long> {

    Optional<Nutrition> findByRecipeRecipeId(Long recipeid);

}
