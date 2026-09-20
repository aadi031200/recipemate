package com.project.recipemate.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "substitutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Substitution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String ingredientName;   // lowercase, e.g. "egg"

    @Column(nullable = false)
    private String substituteName;   // e.g. "flax egg (1 tbsp ground flax + 3 tbsp water)"

    private String notes;
}
