package com.project.recipemate.controller;

import com.project.recipemate.dtos.SubstitutionResponseDTO;
import com.project.recipemate.service.SubstitutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/substitutions")
public class SubstitutionController {
    private final SubstitutionService substitutionService;

    public SubstitutionController(SubstitutionService substitutionService) {
        this.substitutionService = substitutionService;
    }

    @GetMapping
    public ResponseEntity<SubstitutionResponseDTO> getSubstitute(@RequestParam String ingredient) {
        return ResponseEntity.ok(substitutionService.getSubstitute(ingredient));
    }
}
