
package com.example.cookingapp.controller;

import com.example.cookingapp.dto.RecipeDto;
import com.example.cookingapp.dto.request.RequestDto;
import com.example.cookingapp.dto.response.RecipeLogDto;
import com.example.cookingapp.entity.RecipeLog;
import com.example.cookingapp.service.RecipeService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/V1/recipes")
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public RecipeDto generateRecipe (@RequestBody RequestDto requestDto) throws Exception {
        log.info("generateRecipe - {}", requestDto);
        return recipeService.generateRecipe(requestDto.getPeople(),requestDto.getPrompt());
    }

    @GetMapping
    public List <RecipeLogDto> getRecipes () throws Exception {
        log.info("getRecipes - {}", "None");
        return recipeService.getAllLogs();
    }

    @GetMapping(value = "/{id}")
    public RecipeDto getRecipe ( @Parameter(description = "Id that identify the recipe") @PathVariable(value = "id") UUID id) throws Exception {
        log.info("getRecipe - {}", id);
        return recipeService.getRecipe(id);
    }

}
