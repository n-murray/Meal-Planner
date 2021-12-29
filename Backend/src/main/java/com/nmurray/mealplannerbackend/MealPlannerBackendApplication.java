package com.nmurray.mealplannerbackend;

import com.nmurray.mealplannerbackend.data.models.Recipe;
import com.nmurray.mealplannerbackend.data.models.WeeklyPlan;
import com.nmurray.mealplannerbackend.data.repos.RecipeRepository;
import com.nmurray.mealplannerbackend.data.repos.WeeklyPlanRepository;
import com.nmurray.mealplannerbackend.enums.WeekDays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EntityScan(basePackages={"com.nmurray.mealplannerbackend" })
@EnableMongoRepositories(basePackages={"com.nmurray.mealplannerbackend"})
public class MealPlannerBackendApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MealPlannerBackendApplication.class, args);
        buildTestItems(context);
    }

    private static void buildTestItems(ApplicationContext context) {
        RecipeRepository repo = context.getBean(RecipeRepository.class);
        WeeklyPlanRepository planRepo = context.getBean(WeeklyPlanRepository.class);
        int day = LocalDate.now().getDayOfMonth() - LocalDate.now().getDayOfWeek().getValue() + 1;
        LocalDate thisWeek = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);

        WeeklyPlan plan = planRepo.getWeeklyPlanByWeekCommencing(thisWeek);
        if(plan == null ) {
            planRepo.deleteAll();
            repo.deleteAll();
            repo.save(new Recipe( "Burgers and Chips", LocalTime.of(0,30), Arrays.asList( "Mince", "Buns", "Potatoes"), Arrays.asList("Make patties from mince", "Cut potatoes into chips", "Cook", "Assemble burgers")));
            repo.save(new Recipe( "Chicken Curry", LocalTime.of(0,45), Arrays.asList( "Chicken", "Sauce", "Rice"), Arrays.asList("Prepare chicken", "Make sauce", "Add ingredients", "Cook rice")));
            Map<String, String> mealMap = new HashMap<String, String>() {{
                put(WeekDays.MONDAY.name(), "Burgers and Chips");
                put(WeekDays.TUESDAY.name(), "Chicken Curry");
                put(WeekDays.WEDNESDAY.name(), "Burgers and Chips");
                put(WeekDays.THURSDAY.name(), "Chicken Curry");
                put(WeekDays.FRIDAY.name(), "Burgers and Chips");
                put(WeekDays.SATURDAY.name(), "Chicken Curry");
                put(WeekDays.SUNDAY.name(), "Burgers and Chips");
            }};

            planRepo.save(new WeeklyPlan(thisWeek, mealMap));
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("PUT", "GET", "OPTIONS","DELETE", "POST");
            }
        };
    }
}
