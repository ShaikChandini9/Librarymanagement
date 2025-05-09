package com.springboot.librarymanagement.request;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String role;  // Role as a String, can be converted to Role Enum in service layer

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
