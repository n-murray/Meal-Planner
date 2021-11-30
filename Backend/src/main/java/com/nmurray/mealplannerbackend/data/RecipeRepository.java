package com.nmurray.mealplannerbackend.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalTime;
import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    public Recipe getRecipeByName(String name);

    public List<Recipe> getRecipesByIngredientsContaining(String ingredient);

    public List<Recipe> getRecipeByCookingTimeIsLessThanEqual(LocalTime time);
}
