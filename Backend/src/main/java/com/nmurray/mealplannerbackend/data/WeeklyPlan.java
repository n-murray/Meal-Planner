package com.nmurray.mealplannerbackend.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.Map;

public class WeeklyPlan {

    @Id
    @Getter
    @Setter
    private String id;

    // The first day of the week
    @Getter
    @Setter
    private Date weekCommencing;

    // The planned meals for the week
    @Getter
    @Setter
    private Map<String, Recipe> meals;

}
