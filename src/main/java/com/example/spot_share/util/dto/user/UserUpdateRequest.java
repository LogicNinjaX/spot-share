package com.example.spot_share.util.dto.user;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UserUpdateRequest {

    @NotEmpty(message = "required field")
    @Size(min = 5, max = 30, message = "username must be between 5-30 characters")
    private String username;

    @NotEmpty(message = "required field")
    @Size(min = 8, message = "password must be 8 characters long or more")
    private String password;

    @NotEmpty(message = "required field")
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
