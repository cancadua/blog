package com.blogbackend.services;

import com.blogbackend.models.*;
import com.blogbackend.payload.request.SignupRequest;
import com.blogbackend.payload.response.MessageResponse;
import com.blogbackend.repositories.RoleRepository;
import com.blogbackend.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WithMockUser
public class AuthServiceTest {
    @InjectMocks
    AuthService authService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @AfterEach
    void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    void registerUserTest() {
        SignupRequest signupRequest = new SignupRequest("user", "email@mail.com", "password");
        authService.encoder = mock(PasswordEncoder.class);

        when(userRepository.save(any(User.class))).thenReturn(new User("user"));
        when(roleRepository.findByName(any())).thenReturn(Optional.of(new Role()));
        MessageResponse messageResponse = authService.registerUser(signupRequest);

        assertEquals("Registered successfully!", messageResponse.getMessage());
    }
}
