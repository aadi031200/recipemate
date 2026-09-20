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
@Table(name="ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Ingridient_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false) // Double type lets you safely run math functions like: quantity * (targetServings / baseServings)
    private Double quantity;

    @Column(nullable = false)
    private String unit; // e.g., "g", "ml", "tbsp"

    @Column(name = "substitutable_flag", nullable = false)
    private Boolean substitutableFlag = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @JsonBackReference("recipe-ingredients")
    private Recipe recipe;


}
