package com.springboot.librarymanagement.serviceimpl;

import com.nimbusds.jose.JOSEException;
import com.springboot.librarymanagement.entity.Role;
import com.springboot.librarymanagement.entity.User;
import com.springboot.librarymanagement.repository.UserRepository;
import com.springboot.librarymanagement.request.UserRequest;
import com.springboot.librarymanagement.response.UserResponse;
import com.springboot.librarymanagement.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User addUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword()); // You can encrypt here
        user.setRole(Role.valueOf(userRequest.getRole().toUpperCase()));

        return userRepository.save(user);
    }

    // Register user
    public UserResponse registerUser(UserRequest userRequest) throws JOSEException {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());  // No encryption, ideally encrypt this
        user.setRole(Role.valueOf(userRequest.getRole().toUpperCase()));  // Assign role
        userRepository.save(user);

        // Generate JWT token for the registered user
        String token = jwtTokenProvider.generateToken(user);

        // Create and return user response
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getUserid());
        userResponse.setUsername(user.getUsername());
        userResponse.setRole(user.getRole().toString());
        userResponse.setToken(token);

        return userResponse;
    }

    // Login user
    public UserResponse loginUser(UserRequest userRequest) throws JOSEException {
        Optional<User> userOptional = userRepository.findByUsername(userRequest.getUsername());
        if (userOptional.isEmpty() || !userOptional.get().getPassword().equals(userRequest.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = userOptional.get();
        String token = jwtTokenProvider.generateToken(user);

        // Create and return user response
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getUserid());
        userResponse.setUsername(user.getUsername());
        userResponse.setRole(user.getRole().toString());
        userResponse.setToken(token);

        return userResponse;
    }
}
