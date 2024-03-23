package com.example.petconnect.services.post;

import com.example.petconnect.models.ExtendedPost;

import java.util.ArrayList;

public class GetPostResponse {
    private String message;
    private ArrayList<ExtendedPost> data;

    public String getMessage() {
        return this.message;
    }

    public ArrayList<ExtendedPost> getPostList() {
        return this.data;
    }
}
