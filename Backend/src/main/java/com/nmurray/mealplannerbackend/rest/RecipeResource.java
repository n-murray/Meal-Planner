package com.nmurray.mealplannerbackend.rest;

import com.nmurray.mealplannerbackend.common.validator.Validator;
import com.nmurray.mealplannerbackend.data.models.Recipe;
import com.nmurray.mealplannerbackend.data.repos.RecipeRepository;
import com.nmurray.mealplannerbackend.enums.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/recipe")
public class RecipeResource {

    @Autowired
    private RecipeRepository repository;

    @GetMapping()
    public ResponseEntity<?> getAllRecipes() {
        List<Recipe> recipesList = repository.findAll();
        return new ResponseEntity<>(recipesList, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getRecipe(@PathVariable String name) {
        Recipe recipe = repository.getRecipeByName(name);
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveRecipe( @RequestBody Recipe newRecipe) {
        String validation = validateRecipe(newRecipe);
        if(validation.equals("valid")) {
            Recipe recipe = repository.save(newRecipe);
            return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteRecipe( @RequestBody Recipe recipe) {
        try {
            repository.delete(recipe);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(IllegalArgumentException ie) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String validateRecipe( Recipe newRecipe ) {
        Validator val = new Validator();
        String name = newRecipe.getName();
        if(!val.checkString(name)) {
            return Messages.INVALID_RECIPE.getLabel();
        } else if( newRecipe.getIngredients().size() == 0 || !val.checkList(newRecipe.getIngredients())) {
            return Messages.INVALID_RECIPE.getLabel();
        } else if( newRecipe.getPreparationSteps().size() == 0 || !val.checkList(newRecipe.getPreparationSteps())) {
            return Messages.INVALID_RECIPE.getLabel();
        } else if( newRecipe.getCookingTime() == null || newRecipe.getCookingTime() == LocalTime.of(0,0,0)) {
            return Messages.INVALID_RECIPE.getLabel();
        } else if( repository.getRecipeByName(name) != null ) {
            return Messages.INVALID_NAME.getLabel();
        }

        return "valid";
    }

}
