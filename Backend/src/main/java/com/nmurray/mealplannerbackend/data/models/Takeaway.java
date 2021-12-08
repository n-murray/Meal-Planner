package com.nmurray.mealplannerbackend.data.models;

import com.nmurray.mealplannerbackend.data.Meal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public class Takeaway implements Meal {

    @Id
    @Getter
    @Setter
    private String id;

    @Setter
    private String name;

    public Takeaway(String name) {
        setName(name);
    }

    public Takeaway() {}

    @Override
    public String getName() {
        return this.name;
    }
}
