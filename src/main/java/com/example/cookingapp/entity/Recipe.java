package com.example.cookingapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36)
    private UUID id;

    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    @Column(nullable = false)
    private int serves;

    @Column(nullable = false)
    private int noOfGramsOnePersonEats;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "recipe_instructions",
            joinColumns = @JoinColumn(name = "recipe_id")
    )
    @Column(name = "instruction", length = 2000)
    private List<String> instructions = new ArrayList<>();

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RecipeLog recipeLog;

    public void setRecipeLog(RecipeLog log) {
        this.recipeLog = log;
        if (log != null) {
            log.setRecipe(this);
        }
    }
}
