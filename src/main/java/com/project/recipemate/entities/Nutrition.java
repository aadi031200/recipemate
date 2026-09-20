package com.project.recipemate.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="nutrition")
public class Nutrition {

    @Id
    private Long Nutrition_id; // No GenerationType here; it shares the ID with Recipe

    private Double calories;
    private Double protein;
    private Double fat;
    private Double carbs;
    private Double sugar;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Ensures the primary key matches the parent Recipe's primary key
    @JoinColumn(name = "recipe_id")
    @JsonBackReference("recipe-nutrition")
    private Recipe recipe;
}
