package com.blogbackend.controllers;

import com.blogbackend.repositories.UserRepository;
import com.blogbackend.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getSelfUser() {
        return ResponseEntity.ok(
                userDetailsService.loadUserByUsername(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(username), HttpStatus.OK);
    }
}
