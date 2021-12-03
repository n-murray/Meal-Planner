package com.nmurray.mealplannerbackend.data;

public interface TakeawayRepository extends MealRepository {

    public Takeaway getTakeawayByName(String name);
}
