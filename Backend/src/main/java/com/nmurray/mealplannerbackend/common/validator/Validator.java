package com.nmurray.mealplannerbackend.common.validator;

import com.nmurray.mealplannerbackend.data.repos.RecipeRepository;
import com.nmurray.mealplannerbackend.data.repos.TakeawayRepository;
import com.nmurray.mealplannerbackend.enums.Messages;
import com.nmurray.mealplannerbackend.enums.WeekDays;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Validator {

    public boolean checkList( List<String> list ) {
        for(String i : list) {
            if(!checkString(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkString( String string) {
        return string != null && !string.equals("");
    }

    public String checkMealMap( RecipeRepository recipeRepo, TakeawayRepository takeAwayRepo, Map<String, String> map) {
        AtomicReference<String> valid = new AtomicReference<>("valid");
        map.forEach((k,v) -> {
            if(!checkString(k) || !checkWeekDay(k)) {
                valid.set(Messages.INVALID_WEEKDAY.getLabel());
            } else if(!checkString(k) || ( recipeRepo.getRecipeByName(v) == null && takeAwayRepo.getTakeawayByName(v) == null)) {
                valid.set(Messages.INVALID_MEAL.getLabel());
            }
        });
        return valid.get();
    }

    private boolean checkWeekDay(String weekDay) {
        try {
            WeekDays.valueOf(weekDay);
        } catch (IllegalArgumentException ie) {
            return false;
        }

        return true;
    }
}
