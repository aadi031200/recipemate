package com.project.recipemate.dtos.groq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroqChoiceDTO {
    private GroqMessageDTO message;
}
