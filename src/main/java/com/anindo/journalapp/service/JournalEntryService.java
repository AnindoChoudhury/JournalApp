package com.anindo.journalapp.service;

import com.anindo.journalapp.entity.JournalEntry;
import com.anindo.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
     @Autowired
     private JournalEntryRepository journalEntryRepository;

     public void saveEntry(JournalEntry journalEntry){
         journalEntry.setDate(LocalDateTime.now());
         journalEntryRepository.save(journalEntry);
     }

     public List<JournalEntry>getAll(){
         return journalEntryRepository.findAll();
     }

     public Optional<JournalEntry> findById(ObjectId id){
         return journalEntryRepository.findById(id);
     }

     public void deleteById(ObjectId id){
         journalEntryRepository.deleteById(id);
     }

     public void putById(ObjectId id, JournalEntry newEntry){
         Optional<JournalEntry> optionalEntry = journalEntryRepository.findById(id);
         if(optionalEntry.isPresent()) {
             JournalEntry oldEntry = optionalEntry.get();

             if (!newEntry.getTitle().isEmpty()) {
                 oldEntry.setTitle(newEntry.getTitle());
             }

             if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                 oldEntry.setContent(newEntry.getContent());
             }

             journalEntryRepository.save(oldEntry);
         }
     }
}
