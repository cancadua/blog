package com.blogbackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    private String username;
 
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
