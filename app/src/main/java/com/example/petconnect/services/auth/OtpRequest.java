package com.example.petconnect.services.auth;

import retrofit2.Callback;

public class OtpRequest {
    private String email;
    private String token;

    public OtpRequest(String email, String token) {
        this.email = email;
        this.token = token;
    }

}
