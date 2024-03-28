package com.example.petconnect.services.comment;


import com.example.petconnect.models.ExtendedPost;

import java.util.ArrayList;

public class GetAddCommentResponse {
    private String message;
    private ArrayList<ExtendedPost> data;

    public String getMessage() {
        return this.message;
    }

    public ArrayList<ExtendedPost> getPostList() {
        return this.data;
    }
}
