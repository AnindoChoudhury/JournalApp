package com.anindo.journalapp.controllers;

import com.anindo.journalapp.entity.JournalEntry;
import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.service.JournalEntryService;
import com.anindo.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;


    // Get all journal entries of a particular username
    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        List<JournalEntry> all = journalEntryService.getAll(username);
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id); // box containing the document
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> postJournalEntry(@RequestBody JournalEntry journalEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assert authentication != null;
            String username = authentication.getName();
            journalEntryService.saveEntry(journalEntry,username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id){
        try {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
            String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
            Optional<User> user = Optional.ofNullable(userService.findByUsername(username));
            if(journalEntry.isPresent() && user.isPresent()) {
                journalEntryService.deleteById(id,username);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(Map.of(
                        "message", "wrong id"
                ),HttpStatus.BAD_REQUEST);
            }
        }
        catch(Exception e){
           return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/id/{id}")
    public ResponseEntity<?> putById(@PathVariable ObjectId id,@RequestBody JournalEntry journalEntry){
        try {
            journalEntryService.putById(id, journalEntry);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
