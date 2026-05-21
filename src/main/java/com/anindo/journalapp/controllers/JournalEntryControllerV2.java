package com.anindo.journalapp.controllers;

import com.anindo.journalapp.entity.JournalEntry;
import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.service.JournalEntryService;
import com.anindo.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        List<JournalEntry> all = journalEntryService.getAll();
        if(!all.isEmpty()){
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

    @PostMapping("{username}")
    public ResponseEntity<?> postJournalEntry(@RequestBody JournalEntry journalEntry, @PathVariable String username){
        try {
            journalEntryService.saveEntry(journalEntry,username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{username}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id,@PathVariable String username){
        try {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
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
