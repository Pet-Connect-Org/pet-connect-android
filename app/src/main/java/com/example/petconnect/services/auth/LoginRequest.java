package com.example.petconnect.services.auth;

public class LoginRequest {
    private String email;
    private String password;
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
