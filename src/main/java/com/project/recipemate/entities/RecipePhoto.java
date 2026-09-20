package com.project.recipemate.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="recipe_photos")
public class RecipePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Photo_id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "taken_at", updatable = false)
    private Instant takenAt = Instant.now();

    private String caption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

}
