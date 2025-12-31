package com.example.cookingapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeLogDto {

    private UUID id;
    private String recipeName;
    private String prompt;
    private int requestedPeople;
    private LocalDateTime createdAt;
    private UUID recipeId;
}
