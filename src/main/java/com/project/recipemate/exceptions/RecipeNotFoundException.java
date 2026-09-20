package com.project.recipemate.exceptions;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(String message) {
        super(message);
    }
}
