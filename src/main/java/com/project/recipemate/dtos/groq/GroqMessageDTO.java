package com.project.recipemate.dtos.groq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GroqMessageDTO {

    private String role;
    private String content;
    public GroqMessageDTO(){}

    public GroqMessageDTO(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
