package com.springboot.librarymanagement.response;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String role;
    private String token;  // JWT token for authentication
    private String message;  // For error messages
    private String details;  // To carry additional error details

    // Default constructor
    public UserResponse() {}

    // Constructor for success response
    public UserResponse(Long id, String username, String role, String token) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = token;
    }

    // Constructor for error response
    public UserResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
