package com.nmurray.mealplannerbackend.rest

import com.nmurray.mealplannerbackend.MealPlannerBackendApplication
import com.nmurray.mealplannerbackend.data.models.Takeaway
import com.nmurray.mealplannerbackend.data.repos.TakeawayRepository
import com.nmurray.mealplannerbackend.enums.Messages
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(classes = MealPlannerBackendApplication.class)
class TakeawayResourceTest extends Specification {

    @Shared
    def takeaway1 = new Takeaway( "Chinese")
    @Shared
    def takeaway2 = new Takeaway( "Chippy")
    @Shared
    def takeawayList = (List<Takeaway>) Arrays.asList(takeaway1, takeaway2)
    @Shared
    def badTakeaway = new Takeaway("")

    @Autowired
    TakeawayRepository repo

    @Autowired
    TakeawayResource takeawayResource

    def setup() {
        repo.deleteAll()
    }

    def "When a new Takeaway is received it should be persisted" () {
        given: "A Takeaway"
        when: "The saveTakeaway endpoint is called"
            def response = takeawayResource.saveTakeaway(newTakeaway)
        then: "The Takeaway is persisted and the correct response is received"
            response.statusCodeValue == statusCode
            response.body == result
        where:
            newTakeaway  | statusCode | result
            takeaway1    | 201        | takeaway1
            badTakeaway  | 400        | Messages.INVALID_TAKEAWAY.getLabel()
    }

    def "When a new Takeaway is received with an existing name it should not be persisted" () {
        given: "A Takeaway with an existing name"
            repo.save(takeaway1)
        when: "The saveTakeaway endpoint is called"
        def response = takeawayResource.saveTakeaway(takeaway1)
        then: "The Takeaway is persisted and the correct response is received"
        response.statusCodeValue == 400
        response.body == Messages.INVALID_NAME.getLabel()
    }

    def "When get all Takeaways is called, all Takeaways should be returned" () {
        given: "A Takeaway repository"
            repo.saveAll(takeawayList)
        when: "The get all Takeaways endpoint is called"
            def response = takeawayResource.getAllTakeaways()
        then: "All the Takeaways should be returned, with the correct status"
            response.statusCodeValue == 200
            response.body.size() == 2
    }

    def "When get Takeaway is called, the correct Takeaway should be returned" () {
        given: "A Takeaway repository with Takeaways"
            repo.saveAll(takeawayList)
        when: "The get Takeaway endpoint is called"
            def response = takeawayResource.getTakeaway(takeaway1.getName())
        then: "The Takeaway should be returned, with the correct status"
            response.statusCodeValue == 200
            response.body.name == takeaway1.getName()
    }

    def "When the delete endpoint is called it should return the correct response" () {
        given: "A Takeaway repository"
        when: "The delete endpoint is called"
            def response = takeawayResource.deleteTakeaway(takeawayToDelete)
        then: "The correct response should be given"
            response.statusCodeValue == statusCode
        where:
            takeawayToDelete  | statusCode
            takeaway1         | 200
            null              | 400
    }

}