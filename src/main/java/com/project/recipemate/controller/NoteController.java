package com.project.recipemate.controller;

import com.project.recipemate.dtos.NoteRequestDTO;
import com.project.recipemate.dtos.NoteResponseDTO;
import com.project.recipemate.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/recipes/{recipeId}/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<NoteResponseDTO> addNote(
            @PathVariable Long userId,
            @PathVariable Long recipeId,
            @RequestBody NoteRequestDTO request){
        return ResponseEntity.ok(noteService.addNote(userId, recipeId, request));
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getNotes(
            @PathVariable Long userId,
            @PathVariable Long recipeId){
            return ResponseEntity.ok(noteService.getNotesForRecipe(userId, recipeId));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId){
        noteService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}
