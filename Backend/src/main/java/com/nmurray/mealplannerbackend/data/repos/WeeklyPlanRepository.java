package com.nmurray.mealplannerbackend.data.repos;

import com.nmurray.mealplannerbackend.data.models.WeeklyPlan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;

public interface WeeklyPlanRepository extends MongoRepository<WeeklyPlan, String> {

    public WeeklyPlan getWeeklyPlanByWeekCommencing(LocalDate weekCommencing);
}
