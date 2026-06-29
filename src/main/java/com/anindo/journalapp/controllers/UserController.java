package com.anindo.journalapp.controllers;

import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.response.QuoteResponse;
import com.anindo.journalapp.response.TemperatureResponse;
import com.anindo.journalapp.service.QuoteService;
import com.anindo.journalapp.service.UserService;
import com.anindo.journalapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private QuoteService quoteService;

    // Update user
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        userService.putUser(authentication.getName(),user.getUsername(),user.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        try {
            userService.deleteByUsername();
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> greetings(){
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            assert auth != null;
            TemperatureResponse temperatureResponse = weatherService.getWeather("Kolkata");
            QuoteResponse quoteResponse = quoteService.callQuoteAPI();

            String author = quoteResponse.getAuthor().getName();
            String quote = quoteResponse.getQuote();

            String temperatureStr = " The temperature feels like " + temperatureResponse.getCurrent().getTemperature();

            String quoteOfTheDay = "\n" +  quote + "\n-" + author;

            return new ResponseEntity<>("hello " + auth.getName() + temperatureStr + quoteOfTheDay, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
