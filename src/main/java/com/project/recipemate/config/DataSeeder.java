package com.project.recipemate.config;

import com.project.recipemate.entities.Substitution;
import com.project.recipemate.repository.SubstitutionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final SubstitutionRepository substitutionRepository;

    public DataSeeder(SubstitutionRepository substitutionRepository) {
        this.substitutionRepository = substitutionRepository;
    }

    @Override
    public void run(String... args) {
        if (substitutionRepository.count() > 0) return;  // don't reseed every restart

        List<Substitution> seed = List.of(
                new Substitution(null, "egg", "flax egg (1 tbsp ground flax + 3 tbsp water)", "Best for baking"),
                new Substitution(null, "milk", "almond milk or soy milk", "1:1 ratio"),
                new Substitution(null, "butter", "coconut oil or vegan butter", "1:1 ratio"),
                new Substitution(null, "paneer", "firm tofu", "Similar texture when pan-fried"),
                new Substitution(null, "yogurt", "coconut yogurt or cashew yogurt", "1:1 ratio"),
                new Substitution(null, "chicken", "jackfruit or soy chunks", "For vegetarian version"),
                new Substitution(null, "honey", "maple syrup or agave nectar", "1:1 ratio, vegan-friendly"),
                new Substitution(null, "cream", "coconut cream", "For rich curries and desserts")
        );

        substitutionRepository.saveAll(seed);
    }
}
