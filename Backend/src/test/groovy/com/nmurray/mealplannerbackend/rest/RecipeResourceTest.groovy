package com.nmurray.mealplannerbackend.rest

import com.nmurray.mealplannerbackend.MealPlannerBackendApplication
import com.nmurray.mealplannerbackend.data.Recipe
import com.nmurray.mealplannerbackend.data.RecipeRepository
import com.nmurray.mealplannerbackend.enums.Messages
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalTime;

@SpringBootTest(classes = MealPlannerBackendApplication.class)
class RecipeResourceTest extends Specification {

    @Shared
    def recipe1 = new Recipe( "Burgers and Chips", LocalTime.of(0,30), Arrays.asList( "Mince", "Buns", "Potatoes"), Arrays.asList("Make patties from mince", "Cut potatoes into chips", "Cook", "Assemble burgers"))
    @Shared
    def recipe2 = new Recipe( "Chicken Curry", LocalTime.of(0,45), Arrays.asList( "Chicken", "Sauce", "Rice"), Arrays.asList("Prepare chicken", "Make sauce", "Add ingredients", "Cook rice"))
    @Shared
    def recipeList = (List<Recipe>) Arrays.asList(recipe1, recipe2);
    @Shared
    def bad_recipe = new Recipe( "", LocalTime.of(0,45), Arrays.asList( "Chicken", "Sauce", "Rice"), Arrays.asList("Prepare chicken", "Make sauce", "Add ingredients", "Cook rice"))

    RecipeRepository repo = Mock()

    def recipeResource = new RecipeResource()

    def setup() {
        recipeResource.repository = repo
    }

    def "When a new recipe is received it should be persisted" () {
        given: "A mock recipe repository"
            repo.save(recipe1) >> recipe1
        when: "The saveRecipe endpoint is called"
            def response = recipeResource.saveRecipe(newRecipe)
        then: "The recipe is persisted and the correct response is received"
            response.statusCodeValue == statusCode
            response.body == result
        where:
            newRecipe  | statusCode | result
            recipe1    | 201        | recipe1
            bad_recipe | 400        | Messages.INVALID_RECIPE.getLabel()
    }

    def "When get all recipes is called, all recipes should be returned" () {
        given: "A mock recipe repository"
            repo.findAll() >> recipeList
        when: "The get all recipes endpoint is called"
            def response = recipeResource.getAllRecipes()
        then: "All the recipes should be returned, with the correct status"
            response.statusCodeValue == 200
            response.body == recipeList
    }

    def "When the delete endpoint is called it should return the correct response" () {
        given: "A mock recipe repository"
            repo.delete(null ) >> { throw new IllegalArgumentException()}
        when: "The delete endpoint is called"
            def response = recipeResource.deleteRecipe(recipeToDelete)
        then: "The correct response should be given"
            response.statusCodeValue == statusCode
        where:
            recipeToDelete  | statusCode
            recipe1         | 200
            null            | 400
    }

}