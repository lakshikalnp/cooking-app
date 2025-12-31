package com.example.cookingapp.mapper;

import com.example.cookingapp.dto.response.RecipeLogDto;
import com.example.cookingapp.entity.RecipeLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeLogMapper {
    @Mapping(target = "recipeId", source = "recipe.id")
    RecipeLogDto toDto(RecipeLog recipeLog);
}
