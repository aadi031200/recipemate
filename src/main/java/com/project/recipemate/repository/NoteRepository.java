package com.project.recipemate.repository;

import com.project.recipemate.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note,Long> {

    List<Note> findByUserUserIdAndRecipeRecipeId(Long userId,Long recipeId);
    List<Note> findByUserUserId(Long userId);
}
