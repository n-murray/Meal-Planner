package com.nmurray.mealplannerbackend.rest;

import com.nmurray.mealplannerbackend.data.Recipe;
import com.nmurray.mealplannerbackend.data.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
        Recipe recipe = repository.save(newRecipe);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteRecipe( @RequestBody Recipe recipe) {
        repository.delete(recipe);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
