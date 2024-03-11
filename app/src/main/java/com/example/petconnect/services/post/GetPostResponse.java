package com.example.petconnect.services.post;

import com.example.petconnect.models.ExtendedAccount;
import com.example.petconnect.models.ExtendedPost;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GetPostResponse {
    private String message;
    private ArrayList<ExtendedPost> data;

    public String getMessage() {
        return this.message;
    }
    public ArrayList<ExtendedPost> getUser() {
        return this.data;
    }
}
