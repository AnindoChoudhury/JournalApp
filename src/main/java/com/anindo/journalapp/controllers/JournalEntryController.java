package com.anindo.journalapp.controllers;

import com.anindo.journalapp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean postJournalEntry(@RequestBody JournalEntry journalEntry){
        long id = journalEntry.getId();
        journalEntries.put(id,journalEntry);
        return true;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getById(@PathVariable Long id){
        return journalEntries.get(id);
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable Long id){
        journalEntries.remove(id);
    }

    @PutMapping("/id/{id}")
    public void putById(@PathVariable Long id,@RequestBody JournalEntry journalEntry){
        journalEntries.put(id,journalEntry);
    }



}
