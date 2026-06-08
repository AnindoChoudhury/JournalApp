package com.anindo.journalapp.service;

import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.Authenticator;
import java.util.List;
import java.util.Objects;
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

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User user){
        user.setRoles(List.of("USER"));
        user.setPassword(Objects.requireNonNull(passwordEncoder.encode(user.getPassword())));
        userRepository.save(user);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByUsername(String username){
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));
        return optionalUser.orElse(null);
    }

    public void putUser(String oldUsername, String newUsername, String newPassword){
        User user = findByUsername(oldUsername);
        if(user != null){
            if(!oldUsername.equals(newUsername))
                user.setUsername(newUsername);

            // user.getPassword() is the encoded password from DB
            // newPassword is the raw password
            if(!passwordEncoder.matches(newPassword,user.getPassword()))
                user.setPassword(Objects.requireNonNull(passwordEncoder.encode(newPassword)));

            userRepository.save(user);
        }


    }

    public void deleteByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        userRepository.deleteByUsername(authentication.getName());
    }
}
