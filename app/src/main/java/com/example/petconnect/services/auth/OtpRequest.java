package com.example.petconnect.services.auth;

public class OtpRequest {
    private String email;
    private String token;

    public OtpRequest(String email, String token, String number3, String number4, String number5, String number6) {
        this.email = email;
        this.token = token;
    }
}
