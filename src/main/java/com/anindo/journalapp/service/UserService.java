package com.anindo.journalapp.service;

import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void saveUser(User user){
        user.setRoles(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public User findByUsername(String username){
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        return optionalUser.orElse(null);
    }

    public boolean putUser(String oldUsername, String newUsername, String newPassword){
        User user = findByUsername(oldUsername);
        if(user != null){
            user.setUsername(newUsername);
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
