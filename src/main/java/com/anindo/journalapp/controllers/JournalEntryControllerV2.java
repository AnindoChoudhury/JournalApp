package com.anindo.journalapp.controllers;

import com.anindo.journalapp.entity.JournalEntry;
import com.anindo.journalapp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return null;
    }

    @PostMapping
    public void postJournalEntry(@RequestBody JournalEntry journalEntry){
          journalEntryService.saveEntry(journalEntry);
    }

    @GetMapping("/id/{id}")
    public JournalEntry getById(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Long id){

    }

    @PutMapping("/id/{id}")
    public void putById(@PathVariable Long id,@RequestBody JournalEntry journalEntry){

    }



}
