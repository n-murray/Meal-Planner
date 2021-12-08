package com.nmurray.mealplannerbackend.data.repos;

import com.nmurray.mealplannerbackend.data.models.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalTime;
import java.util.List;

public interface RecipeRepository extends MongoRepository<Recipe, String> {

    public Recipe getRecipeByName(String name);

    public List<Recipe> getRecipesByIngredientsContaining(String ingredient);

    public List<Recipe> getRecipeByCookingTimeIsLessThanEqual(LocalTime time);
}
