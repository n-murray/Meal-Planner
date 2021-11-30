package com.nmurray.mealplannerbackend.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.time.LocalTime;
import java.util.List;

public class Recipe {

    @Id
    @Getter
    @Setter
    private String id;

    // The name of the recipe
    @Getter
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
}
