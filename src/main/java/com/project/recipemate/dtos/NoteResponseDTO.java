package com.project.recipemate.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseDTO {
    private Long noteId;
    private String content;
    private Instant createdAt;
}
