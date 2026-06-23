package com.anindo.journalapp.controllers;

import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<?> findAllUsers() {
        try {
            List<User> all = userService.findAll();
            if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching all users", e);
        }
    }

    @PostMapping("/new-admin")
    public ResponseEntity<?> newAdmin(@RequestBody User user){
        try {
            userService.newAdmin(user);
            return new ResponseEntity<>("Admin created", HttpStatus.OK);
        }
        catch(Exception e){
            throw new RuntimeException("Problem occurred while creating admin", e);
        }
    }
}
