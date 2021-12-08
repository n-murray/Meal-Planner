package com.nmurray.mealplannerbackend.rest;

import com.nmurray.mealplannerbackend.common.validator.Validator;
import com.nmurray.mealplannerbackend.data.models.WeeklyPlan;
import com.nmurray.mealplannerbackend.data.repos.RecipeRepository;
import com.nmurray.mealplannerbackend.data.repos.TakeawayRepository;
import com.nmurray.mealplannerbackend.data.repos.WeeklyPlanRepository;
import com.nmurray.mealplannerbackend.enums.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/plan")
public class WeeklyPlanResource {

    @Autowired
    private WeeklyPlanRepository weeklyPlanRepo;

    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    private TakeawayRepository takeawayRepo;

    @GetMapping()
    public ResponseEntity<?> getAllWeeklyPlans() {
        List<WeeklyPlan> plansList = weeklyPlanRepo.findAll();
        return new ResponseEntity<>(plansList, HttpStatus.OK);
    }

    @GetMapping("/{week}")
    public ResponseEntity<?> getWeeklyPlan(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate week) {
        WeeklyPlan plan = weeklyPlanRepo.getWeeklyPlanByWeekCommencing(week);
        return new ResponseEntity<>(plan, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveWeeklyPlan(@RequestBody WeeklyPlan newWeeklyPlan) {
        String validation = validateWeeklyPlan(newWeeklyPlan);
        if(validation.equals("valid")) {
            WeeklyPlan recipe = weeklyPlanRepo.save(newWeeklyPlan);
            return new ResponseEntity<>(recipe, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteWeeklyPlan( @RequestBody WeeklyPlan recipe) {
        try {
            weeklyPlanRepo.delete(recipe);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(IllegalArgumentException ie) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String validateWeeklyPlan( WeeklyPlan newWeeklyPlan ) {
        Validator val = new Validator();
        LocalDate weekCommencing = newWeeklyPlan.getWeekCommencing();
        if(weekCommencing.getDayOfWeek() != DayOfWeek.MONDAY) {
            return Messages.INVALID_DATE.getLabel();
        }
        String validMap = val.checkMealMap(recipeRepo, takeawayRepo, newWeeklyPlan.getMeals());
        if(!validMap.equals("valid")) {
            return validMap;
        }

        return "valid";
    }

}
