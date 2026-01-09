package com.example.cookingapp.util;

public class ErrorCodes {

    private ErrorCodes() {
        // prevent instantiation
    }

    public static final String RECIPE_GENERATION_FAILED = "0001";
    public static final String VALIDATION_ERROR = "0002";
    public static final String INVALID_ARGUMENT = "0003";
    public static final String INTERNAL_SERVER_ERROR = "0004";
    public static final String RESOURCE_NOT_FOUND = "0005";
}
