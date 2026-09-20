package com.project.recipemate.dtos.groq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroqRequestDTO {
    private String model;
    private List<GroqMessageDTO > messages;
    private double temperature=0.3;

}
