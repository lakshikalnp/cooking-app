
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
    public List<RecipeDto> generateRecipe(
            int people,
            String prompt,
            Integer noOfGramsOnePersonEats,
            boolean isCustomized) throws Exception {

        // Fetch existing logs using clear branching
        List<RecipeLog> recipeLogs = isCustomized
                ? repository.findAllByPromptAndRequestedPeopleAndNoOfGramsOnePersonEats(
                prompt, people, noOfGramsOnePersonEats)
                : repository.findAllByPromptAndRequestedPeople(prompt, people);

        // If recipe exists → map and return
        if (!recipeLogs.isEmpty()) {
            return recipeLogs.stream()
                    .map(log -> recipeMapper.toDto(log.getRecipe()))
                    .toList();
        }

        // Call AI only when needed
        RecipeDto recipeDto =
                chatGPTClient.getRecipeForPeople(
                        people, prompt, noOfGramsOnePersonEats, isCustomized);

        // Map DTO → Entity
        Recipe recipe = mapToRecipeEntity(recipeDto);

        // Create log entry
        RecipeLog recipeLog = new RecipeLog();
        recipeLog.setRecipe(recipe);
        recipeLog.setRecipeName(recipe.getRecipeName());
        recipeLog.setPrompt(prompt);
        recipeLog.setRequestedPeople(people);
        recipeLog.setNoOfGramsOnePersonEats(recipe.getNoOfGramsOnePersonEats());

        recipe.setRecipeLog(recipeLog);

        // Persist aggregate root
        recipeRepository.save(recipe);

        return List.of(recipeDto);
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

    private Recipe mapToRecipeEntity(RecipeDto dto) {
        Recipe recipe = new Recipe();
        recipe.setRecipeName(dto.getRecipeName());
        recipe.setServes(dto.getServes());
        recipe.setNoOfGramsOnePersonEats(dto.getNoOfGramsOnePersonEats());
        recipe.setInstructions(dto.getInstructions());

        List<Ingredient> ingredients = dto.getIngredients().stream()
                .map(i -> {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(i.getName());
                    ingredient.setAmountGrams(i.getAmountGrams());
                    ingredient.setRecipe(recipe);
                    return ingredient;
                })
                .toList();

        recipe.setIngredients(ingredients);
        return recipe;
    }

}
