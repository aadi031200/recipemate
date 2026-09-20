package com.project.recipemate.service;

import com.project.recipemate.config.GroqClient;
import com.project.recipemate.dtos.SubstitutionResponseDTO;
import com.project.recipemate.entities.Substitution;
import com.project.recipemate.repository.SubstitutionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

// service/SubstitutionService.java
@Service
public class SubstitutionService {

    private final SubstitutionRepository substitutionRepository;
    private final GroqClient groqClient;

    public SubstitutionService(SubstitutionRepository substitutionRepository, GroqClient groqClient) {
        this.substitutionRepository = substitutionRepository;
        this.groqClient = groqClient;
    }

    public SubstitutionResponseDTO getSubstitute(String ingredientName) {

        // Tier 1: static table
        Optional<Substitution> dbMatch = substitutionRepository.findByIngredientNameIgnoreCase(ingredientName.trim());
        if (dbMatch.isPresent()) {
            Substitution sub = dbMatch.get();
            SubstitutionResponseDTO dto = new SubstitutionResponseDTO();
            dto.setIngredient(sub.getIngredientName());
            dto.setSubstitute(sub.getSubstituteName());
            dto.setNotes(sub.getNotes());
            dto.setSource("DATABASE");
            return dto;
        }

        // Tier 2: AI fallback
        return groqClient.generateSubstitution(ingredientName);
    }
}
