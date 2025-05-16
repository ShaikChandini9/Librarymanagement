package com.springboot.librarymanagement.service;

import com.nimbusds.jose.JOSEException;
import com.springboot.librarymanagement.entity.User;
import com.springboot.librarymanagement.request.UserRequest;
import com.springboot.librarymanagement.response.UserResponse;

public interface UserService {

    User addUser(UserRequest userRequest);
    UserResponse registerUser(UserRequest userRequest) throws JOSEException;
    UserResponse loginUser(UserRequest userRequest) throws JOSEException;
}
