package com.anindo.journalapp.service;

import com.anindo.journalapp.entity.JournalEntry;
import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.repository.JournalEntryRepository;
import com.anindo.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
     @Autowired
     private JournalEntryRepository journalEntryRepository;
     @Autowired
     private UserRepository userRepository;
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
             throw new RuntimeException();
         }
     }

     public List<JournalEntry>getAll(String username){
         Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
         if(user.isPresent()){
             return user.get().getJournalEntries();
         }
         return Collections.emptyList();
     }

     public Optional<JournalEntry> findById(ObjectId id){

         return journalEntryRepository.findById(id);
     }

     @Transactional
     public void deleteById(ObjectId id, String username){

         try {
             User user = userService.findByUsername(username);
             boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
             if (removed) {
                 journalEntryRepository.deleteById(id);
                 userService.saveUser(user);
             }
         }
         catch(Exception e){
             System.out.println(e);
             throw new RuntimeException("error while deleting the journal entry",e);
         }
     }

     // The work of this method is to validate whether the journalEntry id provided by the user
    // belongs to that user and not some other user

     public boolean validateJournalEntry(ObjectId id, String username){

         User user = userService.findByUsername(username);
         if(user != null){
             // Filter through the journal entries of the user to find the id provided
             List<JournalEntry> list = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();
             return !list.isEmpty();
         }
         return false; // just to be syntactically correct
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
