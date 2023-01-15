package com.blogbackend;

import com.blogbackend.models.UserDetailsImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

// Cant configure

public class WithMockUserDetailsImplSecurityContextFactory
        implements WithSecurityContextFactory<WithMockUserDetailsImpl> {

    @Override
    public SecurityContext createSecurityContext(WithMockUserDetailsImpl userDetailsImpl) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        UserDetailsImpl principal =
                new UserDetailsImpl(userDetailsImpl.username());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}