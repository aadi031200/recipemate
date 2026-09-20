package com.project.recipemate.repository;

import com.project.recipemate.entities.Substitution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubstitutionRepository extends JpaRepository<Substitution,Long> {
    Optional<Substitution> findByIngredientNameIgnoreCase(String ingredientName);

}
