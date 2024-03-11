package com.example.petconnect.services.auth;

import com.example.petconnect.models.ExtendedAccount;

public class LoginResponse {
    private String message;
    private String token;
    private ExtendedAccount user;
    public String getMessage() {
        return this.message;
    }
    public String getToken() {
        return this.token;
    }
    public ExtendedAccount getUser() {
        return this.user;
    }
}
