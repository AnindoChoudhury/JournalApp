package com.anindo.journalapp.service;

import com.anindo.journalapp.entity.User;
import com.anindo.journalapp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Disabled // Disable the test here
    @Test
    public void testAdd() {
        assertEquals(2, 1 + 4);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({"firu", "anindo", "priyo"})
    public void testFindByUsername(String username) {
        assertNotNull(userRepository.findByUsername(username), "test failed for" + username);
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    void testUserCredentials(String username, String password) {

        System.out.println("Testing with Username: " + username + " and Password: " + password);

        assertNotNull(username);
        assertNotNull(password);
    }


}