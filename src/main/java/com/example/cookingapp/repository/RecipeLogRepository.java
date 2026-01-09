package com.example.cookingapp.repository;

import com.example.cookingapp.entity.RecipeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecipeLogRepository extends JpaRepository<RecipeLog, UUID> {
    Optional<RecipeLog> findByPromptAndRequestedPeople(
            String prompt,
            Integer requestedPeople
    );

    List<RecipeLog> findAllByPromptAndRequestedPeople(String prompt, int people);
    List<RecipeLog> findAllByPromptAndRequestedPeopleAndNoOfGramsOnePersonEats(String prompt, int people, int noOfGramsOnePersonEats);
}