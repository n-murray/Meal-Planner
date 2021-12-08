package com.nmurray.mealplannerbackend.data.repos;

import com.nmurray.mealplannerbackend.data.models.Takeaway;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TakeawayRepository extends MongoRepository<Takeaway, String> {

    public Takeaway getTakeawayByName(String name);
}
