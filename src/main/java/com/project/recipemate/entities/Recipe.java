package com.project.recipemate.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column(unique = true)
    private Long spoonacularId;

    // Recipe entity - add this field
    @Column(unique = true)
    private String searchTerm;  // lowercase, e.g. "pasta"

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String source;      //Tracks: "db","AI","Api"

    @Column(nullable = false, name = "servings_base")
    private int servings;

    @Column(columnDefinition ="TEXT")
    private String instructions;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt=Instant.now();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("recipe-ingredients")
    private List<Ingredient> ingredients;

    @OneToOne(mappedBy ="recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("recipe-nutrition")
    private Nutrition nutrition;


    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    @JsonManagedReference("recipe-saved")
    private List<SavedRecipe> savedByUsers;

}
