package com.project.recipemate.service;

import com.project.recipemate.dtos.NoteRequestDTO;
import com.project.recipemate.dtos.NoteResponseDTO;
import com.project.recipemate.entities.Note;
import com.project.recipemate.entities.Recipe;
import com.project.recipemate.entities.User;
import com.project.recipemate.exceptions.RecipeNotFoundException;
import com.project.recipemate.repository.NoteRepository;
import com.project.recipemate.repository.RecipeRepository;
import com.project.recipemate.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public NoteService(NoteRepository noteRepository,
                       UserRepository userRepository,
                       RecipeRepository recipeRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    public NoteResponseDTO addNote(Long userId, Long recipeId, NoteRequestDTO request){
        User user=userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"+userId));

        Recipe recipe=recipeRepository.findById(recipeId)
                .orElseThrow(()-> new RecipeNotFoundException("Recipe not found"+recipeId));

        Note note=new Note();
        note.setUser(user);
        note.setRecipe(recipe);
        note.setContent(request.getContent());
        note.setCreatedAt(Instant.now());

        note=noteRepository.save(note);
        return mapToDTO(note);
    }

    public List<NoteResponseDTO> getNotesForRecipe(Long userId, Long recipeId){
        return noteRepository.findByUserUserIdAndRecipeRecipeId(userId,recipeId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteNote(Long noteId){
        noteRepository.deleteById(noteId);
    }

    private NoteResponseDTO mapToDTO(Note note){
        NoteResponseDTO dto = new NoteResponseDTO();
        dto.setNoteId(note.getId());
        dto.setContent(note.getContent());
        dto.setCreatedAt(note.getCreatedAt());
        return dto;
    }
}
