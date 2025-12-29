package com.example.cookingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {

    private String recipeName;
    private int serves;
    private List<IngredientDto> ingredients;
    private List<String> instructions;
}
