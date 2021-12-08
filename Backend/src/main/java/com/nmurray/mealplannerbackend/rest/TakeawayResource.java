package com.nmurray.mealplannerbackend.rest;

import com.nmurray.mealplannerbackend.common.validator.Validator;
import com.nmurray.mealplannerbackend.data.models.Takeaway;
import com.nmurray.mealplannerbackend.data.repos.TakeawayRepository;
import com.nmurray.mealplannerbackend.enums.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/takeaway")
public class TakeawayResource {

    @Autowired
    private TakeawayRepository repository;

    @GetMapping()
    public ResponseEntity<?> getAllTakeaways() {
        List<Takeaway> takeawaysList = repository.findAll();
        return new ResponseEntity<>(takeawaysList, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getTakeaway(@PathVariable String name) {
        Takeaway meal = repository.getTakeawayByName(name);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> saveTakeaway( @RequestBody Takeaway newTakeaway) {
        String validation = validateTakeaway(newTakeaway);
        if(validation.equals("valid")) {
            Takeaway Takeaway = repository.save(newTakeaway);
            return new ResponseEntity<>(Takeaway, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(validation, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteTakeaway( @RequestBody Takeaway Takeaway) {
        try {
            repository.delete(Takeaway);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(IllegalArgumentException ie) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private String validateTakeaway( Takeaway newTakeaway ) {
        Validator val = new Validator();
        String name = newTakeaway.getName();
        if(!val.checkString(name)) {
            return Messages.INVALID_TAKEAWAY.getLabel();
        } else if( repository.getTakeawayByName(name) != null ) {
            return Messages.INVALID_NAME.getLabel();
        }
        return "valid";
    }
}
