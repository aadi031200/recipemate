package com.project.recipemate.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt=Instant.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SavedRecipe> savedRecipes;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Note> notes;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<RecipePhoto> photos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
