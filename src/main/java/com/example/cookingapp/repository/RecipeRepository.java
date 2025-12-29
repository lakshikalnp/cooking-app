package com.example.cookingapp.repository;

import com.example.cookingapp.entity.Recipe;
import com.example.cookingapp.entity.RecipeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}