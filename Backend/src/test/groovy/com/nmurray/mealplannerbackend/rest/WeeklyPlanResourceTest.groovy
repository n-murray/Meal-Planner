package com.nmurray.mealplannerbackend.rest

import com.nmurray.mealplannerbackend.MealPlannerBackendApplication
import com.nmurray.mealplannerbackend.data.models.Recipe
import com.nmurray.mealplannerbackend.data.models.WeeklyPlan
import com.nmurray.mealplannerbackend.data.repos.RecipeRepository
import com.nmurray.mealplannerbackend.data.repos.WeeklyPlanRepository
import com.nmurray.mealplannerbackend.enums.Messages
import com.nmurray.mealplannerbackend.enums.WeekDays
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalTime
import java.time.LocalDate

@SpringBootTest(classes = MealPlannerBackendApplication.class)
class WeeklyPlanResourceTest extends Specification {

    @Shared
    def recipe1 = new Recipe( "Burgers and Chips", LocalTime.of(0,30), Arrays.asList( "Mince", "Buns", "Potatoes"), Arrays.asList("Make patties from mince", "Cut potatoes into chips", "Cook", "Assemble burgers"))
    @Shared
    def recipe2 = new Recipe( "Chicken Curry", LocalTime.of(0,45), Arrays.asList( "Chicken", "Sauce", "Rice"), Arrays.asList("Prepare chicken", "Make sauce", "Add ingredients", "Cook rice"))

    @Shared
    Map<String, String> mealMap = new HashMap<String, String>() {{
        put(WeekDays.MONDAY.name(), "Burgers and Chips")
        put(WeekDays.TUESDAY.name(), "Chicken Curry")
        put(WeekDays.WEDNESDAY.name(), "Burgers and Chips")
        put(WeekDays.THURSDAY.name(), "Chicken Curry")
        put(WeekDays.FRIDAY.name(), "Burgers and Chips")
        put(WeekDays.SATURDAY.name(), "Chicken Curry")
        put(WeekDays.SUNDAY.name(), "Burgers and Chips")
    }}

    @Shared
    Map<String, String> badMealMap = new HashMap<String, String>() {{
        put(WeekDays.MONDAY.name(), "Burgers and Chips")
        put(WeekDays.TUESDAY.name(), "Chicken Curry")
        put(WeekDays.WEDNESDAY.name(), "Stew")
        put(WeekDays.THURSDAY.name(), "Chicken Curry")
        put(WeekDays.FRIDAY.name(), "Burgers and Chips")
        put(WeekDays.SATURDAY.name(), "Chicken Curry")
        put(WeekDays.SUNDAY.name(), "Burgers and Chips")
    }}

    @Shared
    Map<String, String> badWeekDayMap = new HashMap<String, String>() {{
        put(WeekDays.MONDAY.name(), "Burgers and Chips")
        put(WeekDays.TUESDAY.name(), "Chicken Curry")
        put("Tomorrow", "Burgers and Chips")
        put(WeekDays.THURSDAY.name(), "Chicken Curry")
        put(WeekDays.FRIDAY.name(), "Burgers and Chips")
        put(WeekDays.SATURDAY.name(), "Chicken Curry")
        put(WeekDays.SUNDAY.name(), "Burgers and Chips")
    }}

    @Shared
    def plan1 = new WeeklyPlan(LocalDate.of(2021, 12, 6), mealMap)
    @Shared
    def plan2 = new WeeklyPlan(LocalDate.of(2021, 11, 29), mealMap)
    @Shared
    def planList = (List<WeeklyPlan>) Arrays.asList(plan1, plan2)
    @Shared
    def badDatePlan = new WeeklyPlan(LocalDate.of(2021, 11, 26), mealMap)
    @Shared
    def badMealPlan = new WeeklyPlan(LocalDate.of(2021, 11, 29), badMealMap)
    @Shared
    def badDayPlan = new WeeklyPlan(LocalDate.of(2021, 11, 29), badWeekDayMap)

    @Autowired
    RecipeRepository recipeRepo

    @Autowired
    WeeklyPlanRepository repo

    @Autowired
    WeeklyPlanResource weeklyPlanResource

    def setup() {
        repo.deleteAll()
        recipeRepo.deleteAll()
        recipeRepo.save(recipe1)
        recipeRepo.save(recipe2)
    }

    def "When a new WeeklyPlan is received it should be persisted" () {
        given: "A WeeklyPlan"
        when: "The saveWeeklyPlan endpoint is called"
            def response = weeklyPlanResource.saveWeeklyPlan(newWeeklyPlan)
        then: "The WeeklyPlan is persisted and the correct response is received"
            response.statusCodeValue == statusCode
            response.body == result
        where:
            newWeeklyPlan  | statusCode | result
            plan1          | 201        | plan1
            badMealPlan    | 400        | Messages.INVALID_MEAL.getLabel()
            badDatePlan    | 400        | Messages.INVALID_DATE.getLabel()
            badDayPlan     | 400        | Messages.INVALID_WEEKDAY.getLabel()
    }

    def "When get all WeeklyPlans is called, all WeeklyPlans should be returned" () {
        given: "A WeeklyPlan repository"
            repo.saveAll(planList)
        when: "The get all WeeklyPlans endpoint is called"
            def response = weeklyPlanResource.getAllWeeklyPlans()
        then: "All the WeeklyPlans should be returned, with the correct status"
            response.statusCodeValue == 200
            response.body.size() == 2
    }

    def "When get WeeklyPlan is called, the correct WeeklyPlan should be returned" () {
        given: "A WeeklyPlan repository with WeeklyPlans"
            repo.saveAll(planList)
        when: "The get WeeklyPlan endpoint is called"
            def response = weeklyPlanResource.getWeeklyPlan(plan1.getWeekCommencing())
        then: "The WeeklyPlan should be returned, with the correct status"
            response.statusCodeValue == 200
            response.body.id == plan1.id
    }

    def "When the delete endpoint is called it should return the correct response" () {
        given: "A WeeklyPlan repository, with a plan persisted"
            repo.save(plan1)
        when: "The delete endpoint is called"
            def response = weeklyPlanResource.deleteWeeklyPlan(planToDelete)
        then: "The correct response should be given"
            response.statusCodeValue == statusCode
        where:
            planToDelete  | statusCode
            plan1         | 200
            null          | 400
    }

}