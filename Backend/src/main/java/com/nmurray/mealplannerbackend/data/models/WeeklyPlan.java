package com.nmurray.mealplannerbackend.data.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeeklyPlan {

    @Id
    @Getter
    @Setter
    private String id;

    // The first day of the week
    @Getter
    @Setter
    private LocalDate weekCommencing;

    // The planned meals for the week
    // Map<Week Day, Meal Name>
    @Getter
    @Setter
    private Map<String, String> meals;

    //Any notes needed for the weekly plan
    @Getter
    @Setter
    private List<String> notes;

    public WeeklyPlan() {}

    public WeeklyPlan(LocalDate weekCommencing, Map<String, String> meals) {
        this(weekCommencing,meals, new ArrayList<>());
    }
    public WeeklyPlan(LocalDate weekCommencing, Map<String, String> meals, List<String> notes) {
        setWeekCommencing(weekCommencing);
        setMeals(meals);
        setNotes(notes);
    }
}
