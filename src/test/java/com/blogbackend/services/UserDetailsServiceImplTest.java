package com.blogbackend.services;

import com.blogbackend.models.User;
import com.blogbackend.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {
    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepository userRepository;


    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    void loadUserByUsernameTest() {
        User chosenUser = new User("user");
        Optional<User> optionalUser = Optional.of(chosenUser);

        when(userRepository.findByUsername(any(String.class))).thenReturn(optionalUser);
        UserDetails returnedUser = userDetailsService.loadUserByUsername(chosenUser.getUsername());

        assertEquals(chosenUser.getUsername(), returnedUser.getUsername());
    }
}
