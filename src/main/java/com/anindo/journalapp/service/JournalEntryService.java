package com.anindo.journalapp.service;

import com.anindo.journalapp.entity.JournalEntry;
import com.anindo.journalapp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JournalEntryService {
     @Autowired
     private JournalEntryRepository journalEntryRepository;

     public void saveEntry(JournalEntry journalEntry){
         journalEntryRepository.save(journalEntry);
     }
}
