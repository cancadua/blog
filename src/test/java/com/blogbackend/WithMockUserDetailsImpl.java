package com.blogbackend;

// Cant configure

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserDetailsImplSecurityContextFactory.class)
public @interface WithMockUserDetailsImpl {
    String username() default "user";
    String password() default "password";
}