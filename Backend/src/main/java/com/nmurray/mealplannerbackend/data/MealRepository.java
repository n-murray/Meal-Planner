package com.nmurray.mealplannerbackend.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalTime;
import java.util.List;

public interface MealRepository extends MongoRepository<Meal, String> {

    public Meal getMealByName(String name);

}
