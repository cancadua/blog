package com.blogbackend.controllers;

import com.blogbackend.models.UserDetailsImpl;
import com.blogbackend.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "testUser")
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void getSelfUser() throws Exception {
        UserDetailsImpl currentUser = new UserDetailsImpl("currentUser");

        when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(currentUser);

        mockMvc.perform(get("/api/users/me")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(currentUser.getUsername()));
    }

    @Test
    void getUser() throws Exception {
        UserDetailsImpl principal = new UserDetailsImpl("user");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);

        UserDetailsImpl selectedUser = new UserDetailsImpl("user");

        when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(selectedUser);

        mockMvc.perform(get("/api/users/" + selectedUser.getUsername())
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(selectedUser.getUsername()));
    }
}