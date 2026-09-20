package com.project.recipemate.dtos.groq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroqResponseDTO {
    private List<GroqChoiceDTO> choices;
}
