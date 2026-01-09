package com.example.cookingapp.exception;

import com.example.cookingapp.util.ErrorCodes;

public class RecipeGenerationException extends RuntimeException {

    private final String errorCode;

    public RecipeGenerationException(String message) {
        super(message);
        this.errorCode = ErrorCodes.RECIPE_GENERATION_FAILED;
    }

    public RecipeGenerationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
