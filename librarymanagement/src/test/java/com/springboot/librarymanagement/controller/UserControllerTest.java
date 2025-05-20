package com.springboot.librarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.springboot.librarymanagement.entity.User;
import com.springboot.librarymanagement.request.UserRequest;
import com.springboot.librarymanagement.response.UserResponse;
import com.springboot.librarymanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(UserControllerTest.TestConfig.class)  // Import the config with mocked beans
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService; // now autowired real mock bean

    private final ObjectMapper objectMapper = new ObjectMapper();

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return org.mockito.Mockito.mock(UserService.class);
        }
    }

    @Test
    void testAddUserSuccess() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("newuser");
        userRequest.setPassword("password123");

        User mockUser = new User();
        mockUser.setUserid(1L);
        mockUser.setUsername("newuser");

        when(userService.addUser(any(UserRequest.class))).thenReturn(mockUser);

        mockMvc.perform(post("/api/users/add-user")  // use full path here
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))
                        .with(csrf()))  // add CSRF token if security enabled
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("newuser"));
    }


    @Test
    void testRegisterUserSuccess() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");

        UserResponse mockResponse = new UserResponse("User registered successfully", "token123");

        org.mockito.Mockito.when(userService.registerUser(org.mockito.ArgumentMatchers.any(UserRequest.class)))
                .thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.details").value("token123"));
    }

    @Test
    void testRegisterUserTokenError() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");

        when(userService.registerUser(any(UserRequest.class)))
                .thenThrow(new JOSEException("Token generation failed"));

        // Act & Assert
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Error generating token"))
                .andExpect(jsonPath("$.details").value("Token generation failed"));
    }

    @Test
    void testLoginUserSuccess() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");

        UserResponse mockResponse = new UserResponse("Login successful", "token123");

        when(userService.loginUser(any(UserRequest.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.details").value("token123"));
    }

    @Test
    void testLoginUserInvalidCredentials() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("wrongpassword");

        when(userService.loginUser(any(UserRequest.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid credentials"))
                .andExpect(jsonPath("$.details").value("Bad credentials"));  // Exception message is returned as token
    }

    @Test
    void testLoginUserTokenGenerationError() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");

        // Mock userService.loginUser to throw JOSEException
        when(userService.loginUser(any(UserRequest.class)))
                .thenThrow(new JOSEException("Token generation failed"));

        mockMvc.perform(post("/api/users/login")
                        .with(csrf()) // include CSRF token if needed
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isInternalServerError()) // HTTP 500
                .andExpect(jsonPath("$.message").value("Error generating token"))
                .andExpect(jsonPath("$.details").value("Token generation failed"));
    }

}
