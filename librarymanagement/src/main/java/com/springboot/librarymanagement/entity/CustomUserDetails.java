package com.springboot.librarymanagement.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    // Custom method to access User object directly
    private final User user;

    // Constructor
    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Return roles as authorities
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    // Return user password
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Return username (or email depending on how you authenticate)
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // Account is not expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Account is not locked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Credentials are not expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // User is enabled
    @Override
    public boolean isEnabled() {
        return user.isEnabled(); // Or just return true
    }
}
