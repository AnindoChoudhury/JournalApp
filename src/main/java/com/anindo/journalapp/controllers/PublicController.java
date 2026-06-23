package com.anindo.journalapp.controllers;


import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
// These are all public endpoint
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }


    //Create a user
    //Give username and password in request body
    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>("User created", HttpStatus.OK);
        }
        catch(Exception e){
            System.out.println(e);
            return new ResponseEntity<>("Error in creating user", HttpStatus.BAD_REQUEST);
        }
    }

}
