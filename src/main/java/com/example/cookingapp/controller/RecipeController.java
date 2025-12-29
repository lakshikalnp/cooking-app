
package com.example.cookingapp.controller;

import com.example.cookingapp.dto.RecipeDto;
import com.example.cookingapp.dto.request.RequestDto;
import com.example.cookingapp.entity.RecipeLog;
import com.example.cookingapp.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/V1/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public RecipeDto generateRecipe (@RequestBody RequestDto requestDto) throws Exception {
        return recipeService.generateRecipe(requestDto.getPeople(),requestDto.getPrompt());
    }

}
