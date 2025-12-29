package com.example.cookingapp.mapper;

import com.example.cookingapp.dto.RecipeDto;
import com.example.cookingapp.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    // Entity → DTO
    RecipeDto toDto(Recipe recipe);

    // DTO → Entity
    Recipe toEntity(RecipeDto recipeDto);
}
