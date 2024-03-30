package com.example.petconnect.services.user;

import com.example.petconnect.models.ExtendedUser;

import java.util.ArrayList;

public class GetUserByIdResponse {
    String message;
    ExtendedUser data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExtendedUser getData() {
        return data;
    }

    public void setData(ExtendedUser data) {
        this.data = data;
    }
}
