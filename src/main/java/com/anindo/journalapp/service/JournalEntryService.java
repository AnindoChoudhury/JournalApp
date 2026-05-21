package com.anindo.journalapp.service;

import com.anindo.journalapp.entity.JournalEntry;
import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
     @Autowired
     private JournalEntryRepository journalEntryRepository;
     @Autowired
     private UserService userService;
     @Transactional
     public void saveEntry(JournalEntry journalEntry,String username){
         try {
             Optional<User> user = Optional.ofNullable(userService.findByUsername(username));
             journalEntry.setDate(LocalDateTime.now());
             JournalEntry saved = journalEntryRepository.save(journalEntry);
             if (user.isPresent()) {
                 user.get().getJournalEntries().add(saved);
                 userService.saveUser(user.get());
             }
         }
         catch(Exception e){
             System.out.println(e);
         }
     }

     public List<JournalEntry>getAll(){
         return journalEntryRepository.findAll();
     }

     public Optional<JournalEntry> findById(ObjectId id){
         return journalEntryRepository.findById(id);
     }

     public void deleteById(ObjectId id, String username){

         User user = userService.findByUsername(username);
         user.getJournalEntries().removeIf(x -> x.getId().equals(id));
         journalEntryRepository.deleteById(id);
         userService.saveUser(user);
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
