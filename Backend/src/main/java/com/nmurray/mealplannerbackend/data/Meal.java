package com.nmurray.mealplannerbackend.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public abstract class Meal {

    @Id
    @Getter
    @Setter
    private String id;

    // The name of the recipe
    @Getter
    @Setter
    private String name;

    public Meal() {}
}
