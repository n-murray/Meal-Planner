package com.nmurray.mealplannerbackend.rest;

import com.nmurray.mealplannerbackend.data.Recipe;
import com.nmurray.mealplannerbackend.data.RecipeRepository;
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

    @PostMapping()
    public ResponseEntity<?> saveRecipe( @RequestBody Recipe newRecipe) {
        if(validateRecipe(newRecipe)) {
            Recipe recipe = repository.save(newRecipe);
            return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(Messages.INVALID_RECIPE.getLabel(), HttpStatus.BAD_REQUEST);
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

    private boolean validateRecipe( Recipe newRecipe ) {
        if(!checkString(newRecipe.getName())) {
            return false;
        } else if( newRecipe.getIngredients().size() == 0 || !checkList(newRecipe.getIngredients())) {
            return false;
        } else if( newRecipe.getPreparationSteps().size() == 0 || !checkList(newRecipe.getPreparationSteps())) {
            return false;
        } else if( newRecipe.getCookingTime() == null || newRecipe.getCookingTime() == LocalTime.of(0,0,0)) {
            return false;
        }

        return true;
    }

    private boolean checkList( List<String> list ) {
        for(String i : list) {
            if(!checkString(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkString( String string) {
        if( string.equals("") || string == null) {
            return false;
        } else {
            return true;
        }
    }
}
