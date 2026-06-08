package com.anindo.journalapp.controllers;

import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.service.UserService;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> userList = userService.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // Get by username
    @GetMapping("{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        Optional<User> user = Optional.ofNullable(userService.findByUsername(username));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    // Update user
    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody User user){
        userService.putUser(username,user.getUsername(),user.getPassword());
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

}
