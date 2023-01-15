package com.blogbackend.controllers;

import com.blogbackend.payload.request.LoginRequest;
import com.blogbackend.payload.request.SignupRequest;
import com.blogbackend.payload.response.JwtResponse;
import com.blogbackend.payload.response.MessageResponse;
import com.blogbackend.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "testUser")
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void authenticateUserTest() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user", "password");

        when(authService.authenticateUser(any(LoginRequest.class))).thenReturn(new JwtResponse(loginRequest.getUsername()));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String commentJson = objectMapper.writeValueAsString(loginRequest);
        ResultActions result = mockMvc.perform(post("/api/auth/signin")
                .contentType("application/json")
                .content(commentJson)
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user"));
    }

    @Test
    void registerUserTest() throws Exception {
        SignupRequest signupRequest = new SignupRequest("user", "email@mail.com", "password");

        when(authService.registerUser(any(SignupRequest.class))).thenReturn(new MessageResponse("Registered successfully!"));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String commentJson = objectMapper.writeValueAsString(signupRequest);
        ResultActions result = mockMvc.perform(post("/api/auth/signup")
                .contentType("application/json")
                .content(commentJson)
        );

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Registered successfully!"));
    }
}