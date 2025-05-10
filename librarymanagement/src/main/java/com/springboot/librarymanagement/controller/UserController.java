package com.springboot.librarymanagement.controller;

import com.springboot.librarymanagement.entity.User;
import com.springboot.librarymanagement.request.UserRequest;
import com.springboot.librarymanagement.response.UserResponse;
import com.springboot.librarymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Endpoint to add user
    @PostMapping("/add-user")
    public User addUser(@RequestBody UserRequest userRequest) {
        return userService.addUser(userRequest);
    }

    // Endpoint to register a new user
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        try {
            // Register user and generate JWT token
            UserResponse userResponse = userService.registerUser(userRequest);
            return ResponseEntity.ok(userResponse);  // Return success response with user data and token
        } catch (JOSEException e) {
            return ResponseEntity.status(500).body(new UserResponse("Error generating token", e.getMessage()));  // Handle error
        }
    }

    // Endpoint to Log in a user
    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
        try {
            // Login user and generate JWT token
            UserResponse userResponse = userService.loginUser(userRequest);
            return ResponseEntity.ok(userResponse);  // Return success response with user data and token
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(new UserResponse("Invalid credentials", e.getMessage()));  // Handle invalid credentials error
        } catch (JOSEException e) {
            return ResponseEntity.status(500).body(new UserResponse("Error generating token", e.getMessage()));  // Handle token generation error
        }
    }
}