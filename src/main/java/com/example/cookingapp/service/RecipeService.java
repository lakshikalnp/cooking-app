
package com.example.cookingapp.service;

import com.example.cookingapp.ai.ChatGPTClient;
import com.example.cookingapp.dto.RecipeDto;
import com.example.cookingapp.dto.response.RecipeLogDto;
import com.example.cookingapp.entity.Ingredient;
import com.example.cookingapp.entity.Recipe;
import com.example.cookingapp.entity.RecipeLog;
import com.example.cookingapp.mapper.RecipeLogMapper;
import com.example.cookingapp.mapper.RecipeMapper;
import com.example.cookingapp.repository.RecipeLogRepository;
import com.example.cookingapp.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.cookingapp.util.Constant.PROMPT_STRING;
import static com.example.cookingapp.util.Constant.PROMPT_STRING_WITH_CUSTOMIZED;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeLogRepository repository;
    private final RecipeRepository recipeRepository;
    private final ChatGPTClient chatGPTClient;
    private final RecipeMapper recipeMapper;
    private final RecipeLogMapper recipeLogMapper;


    @Transactional
    public List<RecipeDto> generateRecipe(int people, String prompt, Integer noOfGramsOnePersonEats, boolean isCustomized) throws Exception {
        List<RecipeLog> recipeLog = new ArrayList<>();
        if (!isCustomized)
            recipeLog = repository
                    .findAllByPromptAndRequestedPeople(prompt, people);
        else recipeLog = repository
                .findAllByPromptAndRequestedPeopleAndNoOfGramsOnePersonEats(prompt, people, noOfGramsOnePersonEats);

        if (recipeLog == null || recipeLog.isEmpty()) {
            RecipeDto recipeDto = null;
            recipeDto =
                    chatGPTClient.getRecipeForPeople(people, prompt, noOfGramsOnePersonEats, isCustomized);

            Recipe recipe = new Recipe();
            recipe.setRecipeName(recipeDto.getRecipeName());
            recipe.setServes(recipeDto.getServes());
            recipe.setNoOfGramsOnePersonEats(recipeDto.getNoOfGramsOnePersonEats());
            recipe.setInstructions(recipeDto.getInstructions());

            Recipe finalRecipe = recipe;
            List<Ingredient> ingredients = recipeDto.getIngredients().stream()
                    .map(i -> {
                        Ingredient ing = new Ingredient();
                        ing.setName(i.getName());
                        ing.setAmountGrams(i.getAmountGrams());
                        ing.setRecipe(finalRecipe);
                        return ing;
                    }).toList();

            recipe.setIngredients(ingredients);
            RecipeLog logEntity = new RecipeLog();
            logEntity.setRecipe(recipe); //  FK set here
            logEntity.setRecipeName(recipe.getRecipeName());
            logEntity.setPrompt(prompt);
            logEntity.setRequestedPeople(people);
            logEntity.setNoOfGramsOnePersonEats(recipe.getNoOfGramsOnePersonEats());
            recipe.setRecipeLog(logEntity);
            recipe = recipeRepository.save(recipe); //  save parent first

            return List.of(recipeDto);
        }

        // Existing log → fetch recipe safely
        return recipeLog.stream()
                .map(r -> recipeMapper.toDto(r.getRecipe()))
                .collect(Collectors.toList());
    }

    public Page<RecipeLogDto> getAllLogs(Pageable pageable) {
        return  repository.findAll(pageable)
                .map(recipeLogMapper::toDto);
    }

    public RecipeDto getRecipe(UUID id) {
        return recipeMapper.toDto(recipeRepository.findById(id).orElse(null));
    }


    private String buildPrompt(int people, String prompt) {
        return PROMPT_STRING
                .formatted(prompt, people);
    }

    private String buildPrompt(int people, String prompt, int noOfGramsOnePersonEats) {
        return PROMPT_STRING_WITH_CUSTOMIZED
                .formatted(prompt, people, noOfGramsOnePersonEats);
    }
}
