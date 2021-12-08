package com.nmurray.mealplannerbackend.data.models;

import com.nmurray.mealplannerbackend.data.Meal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalTime;
import java.util.List;

public class Recipe implements Meal {

    @Id
    @Getter
    @Setter
    private String id;

    @Setter
    private String name;

    // The time it takes to prepare this recipe
    @Getter
    @Setter
    private LocalTime cookingTime;

    // List of ingredients needed
    @Getter
    @Setter
    private List<String> ingredients;

    // List of steps to make the dish
    @Getter
    @Setter
    private List<String> preparationSteps;

    public Recipe( String name, LocalTime cookingTime, List<String> ingredients, List<String> preparationSteps ) {
        setName(name);
        setCookingTime(cookingTime);
        setIngredients(ingredients);
        setPreparationSteps(preparationSteps);
    }

    public Recipe() {}

    @Override
    public String getName() {
        return this.name;
    }
}
